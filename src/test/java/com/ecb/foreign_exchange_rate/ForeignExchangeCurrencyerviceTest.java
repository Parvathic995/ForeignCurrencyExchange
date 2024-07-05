package com.ecb.foreign_exchange_rate;


import com.ecb.foreign_exchange_rate.entity.ForexRate;
import com.ecb.foreign_exchange_rate.repository.ForeignExchangeRateRepository;
import com.ecb.foreign_exchange_rate.service.ForeignExchangeRateServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ForeignExchangeCurrencyerviceTest {

    @Mock
    private ForeignExchangeRateRepository repository;

    @InjectMocks
    private ForeignExchangeRateServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetForexRate() throws IOException {
        String targetCurrency = "AUD";
        LocalDate date = LocalDate.now();
        BigDecimal exchangeRate = new BigDecimal("1.4857");
        ForexRate forexRate = new ForexRate(date, "USD", targetCurrency, exchangeRate);

        when(repository.findFirstByTargetCurrencyOrderByDateDesc(targetCurrency))
                .thenReturn(Optional.of(forexRate));

        Optional<ForexRate> result = service.getForexRate(targetCurrency);

        assertEquals(forexRate, result.orElse(null));
        verify(repository, times(1)).findFirstByTargetCurrencyOrderByDateDesc(targetCurrency);
    }

    @Test
    public void testGetLatestForexRate() throws JsonProcessingException {
        String targetCurrency = "EUR";
        List<ForexRate> forexRates = new ArrayList<>();
        forexRates.add(new ForexRate(LocalDate.now(), "USD", targetCurrency, new BigDecimal("0.84663")));
        forexRates.add(new ForexRate(LocalDate.now().minusDays(1), "USD", targetCurrency, new BigDecimal("0.84572")));
        forexRates.add(new ForexRate(LocalDate.now().minusDays(2), "USD", targetCurrency, new BigDecimal("0.84491")));

        when(repository.findLatest3ByTargetCurrencyOrderByDateDesc(targetCurrency))
                .thenReturn(forexRates);

        List<ForexRate> result = service.getLatestForexRate(targetCurrency);

        assertEquals(3, result.size());
        assertEquals(forexRates, result);
        verify(repository, times(1)).findLatest3ByTargetCurrencyOrderByDateDesc(targetCurrency);
    }
}


