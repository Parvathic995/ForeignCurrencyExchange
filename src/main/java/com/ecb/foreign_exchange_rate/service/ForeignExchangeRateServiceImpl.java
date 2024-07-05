package com.ecb.foreign_exchange_rate.service;

import com.ecb.foreign_exchange_rate.entity.ForexRate;
import com.ecb.foreign_exchange_rate.repository.ForeignExchangeRateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ForeignExchangeRateServiceImpl implements ForeignExchangeRateService {
    public static final String API_URL = "https://api.frankfurter.app/latest";
    private static final Logger logger = LoggerFactory.getLogger(ForeignExchangeRateService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ForeignExchangeRateRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<ForexRate> getForexRate(String targetCurrency) throws IOException {
        return Optional.of(repository.findFirstByTargetCurrencyOrderByDateDesc(targetCurrency)
                .orElse(fetchAndSaveForexRate(targetCurrency)));

    }

    @Override
    public List<ForexRate> getLatestForexRate(String targetCurrency) throws JsonProcessingException {
        List<ForexRate> result = repository.findLatest3ByTargetCurrencyOrderByDateDesc(targetCurrency);
        if (result.isEmpty() || result.size() < 3) {
            result = fetchAndSaveLatest3ExchangeRate(targetCurrency);
        }
        logger.info("Fetched Foreign Exchange Rate for Target Currency ");
        return result;
    }

    private ForexRate fetchAndSaveForexRate(String targetCurrency) throws IOException {
        // String url = API_URL + "?base=USD&symbols=" + targetCurrency;
        String url = API_URL + "?from=USD&to=" + targetCurrency;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode jsonNode = objectMapper.readTree(String.valueOf(response.getBody()));
        JsonNode rates = jsonNode.get("rates");
        logger.info(rates.toString());
        BigDecimal exchangeRate = rates.get(targetCurrency).decimalValue();
        ForexRate forexRate = new ForexRate(LocalDate.now(), "USD", targetCurrency, exchangeRate);
        return repository.save(forexRate);
    }

    private List<ForexRate> fetchAndSaveLatest3ExchangeRate(String targetCurrency) throws JsonProcessingException {
        String url = API_URL + "?base=USD&symbols=" + targetCurrency;
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode rates = objectMapper.readTree(String.valueOf(response.getBody()));
        List<ForexRate> forexRates = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            if (rates.has(date.toString())) {
                BigDecimal exchangeRate = rates.get(date.toString()).get(targetCurrency).decimalValue();
                ForexRate forexRate = new ForexRate(date, "USD", targetCurrency, exchangeRate);
                forexRates.add(repository.save(forexRate));
            }
        }
        logger.info("Fetched The Recent 3 Foreign Exchange Rate for Target Currency ");
        return forexRates;
    }


}