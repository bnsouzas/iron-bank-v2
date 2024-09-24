package com.buddycash.ironbank.domain.currencies;

import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AwesomeCurrencyService implements ICurrencyConverterService, ICurrencyAggregatorService {
    private final IAwesomeExchangePricesClient awesomeExchangePricesAPI;

    @Autowired
    public AwesomeCurrencyService(IAwesomeExchangePricesClient awesomeExchangePricesAPI) {
        this.awesomeExchangePricesAPI = awesomeExchangePricesAPI;
    }

    @Override
    public List<CurrencyConvertResponse> aggregate(CurrencyAggregatorRequest request) {
        var exchanges = request.to()
                .parallelStream()
                .map(to -> new CurrencyConvertRequest(request.from(), to, request.value(), request.when()))
                .map(this::convert)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparing(CurrencyConvertResponse::to))
                .collect(Collectors.toList());
        return exchanges;
    }

    @Override
    public Optional<CurrencyConvertResponse> convert(CurrencyConvertRequest request) {
        var dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.systemDefault());
        String exchange = new StringBuilder().append(request.from()).append("-").append(request.to()).toString();
        String referenceDate = dateFormatter.format(request.when());
        var currentExchange = awesomeExchangePricesAPI.dailyPrice(exchange, referenceDate, referenceDate);
        if (currentExchange.isEmpty())
            return Optional.empty();
        var multiplier = currentExchange.getFirst().bid().setScale(2, RoundingMode.HALF_UP);
        var valueIn = request.value().multiply(multiplier);
        var converted = new CurrencyConvertResponse(request.from(), request.to(), request.value(), valueIn, request.when());
        return Optional.of(converted);
    }
}
