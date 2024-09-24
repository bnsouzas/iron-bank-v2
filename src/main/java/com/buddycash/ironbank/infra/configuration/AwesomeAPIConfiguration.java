package com.buddycash.ironbank.infra.configuration;

import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AwesomeAPIConfiguration {
    @Bean
    public IAwesomeExchangePricesClient awesomeExchangePricesAPI() {
        RestClient restClient = RestClient.builder().baseUrl("https://economia.awesomeapi.com.br/").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        IAwesomeExchangePricesClient service = factory.createClient(IAwesomeExchangePricesClient.class);
        return service;
    }
}
