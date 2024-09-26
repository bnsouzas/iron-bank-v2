package com.buddycash.ironbank.domain.currencies;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertRequest;
import com.buddycash.ironbank.tools.FakeAwesomeExchangePricesClient;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AwesomeCurrencyServiceTests {
  private AwesomeCurrencyService awesomeCurrencyService;
  private IAwesomeExchangePricesClient awesomeClient;

  @BeforeEach
  public void init(@Mock IAwesomeExchangePricesClient awesomeClient) {
    this.awesomeClient = awesomeClient;
    this.awesomeCurrencyService = new AwesomeCurrencyService(awesomeClient);
    var fakeClient = new FakeAwesomeExchangePricesClient();
    lenient()
        .when(this.awesomeClient.dailyPrice(any(), any(), any()))
        .then(
            invocationOnMock -> {
              var currency = (String) invocationOnMock.getArgument(0);
              var startDate = (String) invocationOnMock.getArgument(1);
              var endDate = (String) invocationOnMock.getArgument(2);
              return fakeClient.dailyPrice(currency, startDate, endDate);
            });
  }

  @Test
  public void testFoundConvertPrice() {
    var req = new CurrencyConvertRequest("BRL", "USD", BigDecimal.ONE, Instant.now());
    var resp = this.awesomeCurrencyService.convert(req);
    Assertions.assertTrue(resp.isPresent());
    var exchange = resp.get();
    Assertions.assertEquals(req.value(), exchange.valueFrom());
    Assertions.assertEquals(BigDecimal.valueOf(0.18), exchange.valueTo());
  }

  @Test
  public void testNotFoundConvertPrice() {
    var req = new CurrencyConvertRequest("BRL", "MISS", BigDecimal.ONE, Instant.now());
    var resp = this.awesomeCurrencyService.convert(req);
    Assertions.assertTrue(resp.isEmpty());
  }

  @Test
  public void test500ErrorConvertPrice() {
    var req = new CurrencyConvertRequest("BRL", "ERROR", BigDecimal.ONE, Instant.now());
    var resp = this.awesomeCurrencyService.convert(req);
    Assertions.assertTrue(resp.isEmpty());
  }

  @Test
  public void testAggregatePrices() {
    var req =
        new CurrencyAggregatorRequest(
            "BRL", Set.of("USD", "EUR", "MISS"), BigDecimal.ONE, Instant.now());
    var resp = this.awesomeCurrencyService.aggregate(req);
    Assertions.assertEquals(2, resp.size());
    Assertions.assertEquals("EUR", resp.get(0).to());
    Assertions.assertEquals("USD", resp.get(1).to());
  }
}
