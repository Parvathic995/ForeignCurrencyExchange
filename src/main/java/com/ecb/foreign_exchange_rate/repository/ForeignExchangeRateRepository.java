package com.ecb.foreign_exchange_rate.repository;

import com.ecb.foreign_exchange_rate.entity.ForexRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForexRate, Long> {
    Optional<ForexRate> findFirstByTargetCurrencyOrderByDateDesc(String targetCurrency);

    List<ForexRate> findLatest3ByTargetCurrencyOrderByDateDesc(String targetCurrency);
}
