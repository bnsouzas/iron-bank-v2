package com.buddycash.ironbank.domain.currencies.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public record CurrencyAggregatorRequest(
    String from, Set<String> to, BigDecimal value, Instant when) {}
