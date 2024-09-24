package com.buddycash.ironbank.domain.currencies.data;

import java.math.BigDecimal;
import java.time.Instant;

public record CurrencyConvertRequest(String from, String to, BigDecimal value, Instant when){};
