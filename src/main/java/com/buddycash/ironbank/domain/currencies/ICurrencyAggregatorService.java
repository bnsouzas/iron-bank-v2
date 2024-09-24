package com.buddycash.ironbank.domain.currencies;

import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;

import java.util.List;
import java.util.Set;

public interface ICurrencyAggregatorService {
    List<CurrencyConvertResponse> aggregate(CurrencyAggregatorRequest request);
}
