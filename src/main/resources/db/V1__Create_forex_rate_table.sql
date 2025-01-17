-- V1__Create_forex_rate_table.sql
CREATE TABLE forex_rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date DATE NOT NULL,
    source_currency VARCHAR(3) NOT NULL,
    target_currency VARCHAR(3) NOT NULL,
    exchange_rate DECIMAL(10, 6) NOT NULL
);