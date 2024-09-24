package com.buddycash.ironbank.domain.currencies;

import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;

import java.util.Optional;

public interface ICurrencyConverterService {
    Optional<CurrencyConvertResponse> convert(CurrencyConvertRequest request);
}
