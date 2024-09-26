package com.buddycash.ironbank.domain.currencies.clients;

import java.math.BigDecimal;
import java.time.Instant;

public record DailyPriceResponse(
    String code,
    String codein,
    String name,
    BigDecimal high,
    BigDecimal low,
    BigDecimal pctChange,
    BigDecimal bid,
    BigDecimal ask,
    BigDecimal varBid,
    Instant timestamp,
    String createDate) {}
