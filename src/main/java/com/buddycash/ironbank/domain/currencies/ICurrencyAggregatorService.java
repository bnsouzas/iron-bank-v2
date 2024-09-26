package com.buddycash.ironbank.domain.currencies;

import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;
import java.util.List;

public interface ICurrencyAggregatorService {
  List<CurrencyConvertResponse> aggregate(CurrencyAggregatorRequest request);
}
