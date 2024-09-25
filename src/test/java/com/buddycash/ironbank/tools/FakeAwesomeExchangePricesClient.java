package com.buddycash.ironbank.tools;

import com.buddycash.ironbank.domain.currencies.clients.DailyPriceResponse;
import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeAwesomeExchangePricesClient implements IAwesomeExchangePricesClient {
    private final Map<String, DailyPriceResponse> exchangeRates;

    public FakeAwesomeExchangePricesClient() {
        this.exchangeRates = new HashMap<>();
        this.exchangeRates.put("BRL-EUR", new DailyPriceResponse("BRL", "EUR", "Real Brasileiro/Euro", BigDecimal.valueOf(0.1674), BigDecimal.valueOf(0.1656), BigDecimal.valueOf(0), BigDecimal.valueOf(0.1656), BigDecimal.valueOf(0.1656), BigDecimal.valueOf(-0.0006), Instant.parse("2024-08-16T23:59:58Z"), "2024-08-16 20:59:58"));
        this.exchangeRates.put("BRL-USD", new DailyPriceResponse("BRL", "USD", "Real Brasileiro/Dólar Americano", BigDecimal.valueOf(0.1839), BigDecimal.valueOf(0.1822), BigDecimal.valueOf(0.16), BigDecimal.valueOf(0.1826), BigDecimal.valueOf(0.1827), BigDecimal.valueOf(0.0003), Instant.parse("2024-08-16T20:59:42Z"), "2024-08-16 17:59:42"));
        this.exchangeRates.put("BRL-GBP", new DailyPriceResponse("BRL", "USD", "Real Brasileiro/Libra Esterlina", BigDecimal.valueOf(0.1367), BigDecimal.valueOf(0.1367), BigDecimal.valueOf(0.03), BigDecimal.valueOf(0.1366), BigDecimal.valueOf(0.1366), BigDecimal.valueOf(0), Instant.parse("2024-09-24T21:03:32Z"), "2024-09-24 18:03:32"));
    }

    @Override
    public List<DailyPriceResponse> dailyPrice(String currency, String startDate, String endDate) {
        if (currency.equals("BRL-ERROR")) throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        if (!this.exchangeRates.containsKey(currency))
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        var exchange = this.exchangeRates.get(currency);
        return List.of(exchange);
    }
}
