package com.ecb.foreign_exchange_rate.controller;

import com.ecb.foreign_exchange_rate.entity.ForexRate;
import com.ecb.foreign_exchange_rate.service.ForeignExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fx")
public class ForeignExchangeRateController {
    private static final Logger logger = LoggerFactory.getLogger(ForeignExchangeRateService.class);
    @Autowired
    private ForeignExchangeRateService service;

    @GetMapping
    public ForexRate getForexRate(@RequestParam String targetCurrency) throws IOException {
        logger.info("Started ToFetch Foreign Exchange Rate for Target Currency ");
        return service.getForexRate(targetCurrency).get();

    }

    @GetMapping("/{targetCurrency}")
    public List<ForexRate> getLatestForexRate(@PathVariable String targetCurrency) throws JsonProcessingException {
        logger.info("Started To Fetch the 3 recent Foreign Exchange Rate for Target Currency ");
        return service.getLatestForexRate(targetCurrency);
    }
}
