package com.ecb.foreign_exchange_rate.service;

import com.ecb.foreign_exchange_rate.entity.ForexRate;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface ForeignExchangeRateService {
    public Optional<ForexRate> getForexRate(String targetCurrency) throws IOException;

    public List<ForexRate> getLatestForexRate(String targetCurrency) throws JsonProcessingException;
}
