package com.buddycash.ironbank.domain.currencies.clients;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface IAwesomeExchangePricesClient {
    @GetExchange("/daily/{currency}")
    @Cacheable("dailyPrice")
    List<DailyPriceResponse> dailyPrice(@PathVariable("currency") String currency, @RequestParam("start_date") String startDate, @RequestParam("end_date") String endDate);
}
