package com.buddycash.ironbank.domain.transactions.data;

import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;
import java.util.List;

public record TransactionExtraInfo(List<CurrencyConvertResponse> otherCurrencies) {}
