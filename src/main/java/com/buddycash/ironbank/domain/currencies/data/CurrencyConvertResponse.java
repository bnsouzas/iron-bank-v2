package com.buddycash.ironbank.domain.currencies.data;

import java.math.BigDecimal;
import java.time.Instant;

public record CurrencyConvertResponse(
    String from, String to, BigDecimal valueFrom, BigDecimal valueTo, Instant when) {}
