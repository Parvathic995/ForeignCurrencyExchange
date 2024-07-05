package com.ecb.foreign_exchange_rate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "forex_rate")
public class ForexRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String sourceCurrency;
    @Column(nullable = false, unique = true)
    private String targetCurrency;
    @Column(nullable = false)
    private BigDecimal exchangeRate;

    public ForexRate(LocalDate date, String sourceCurrency, String targetCurrency, BigDecimal exchangeRate) {
        this.date = date;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = exchangeRate;

    }


}

