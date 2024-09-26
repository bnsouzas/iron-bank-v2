package com.buddycash.ironbank.domain.transactions.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionCreateRequestTests {
  @Test
  public void transactionCreateRequestWithoutTransactionAt() {
    var request =
        new TransactionCreateRequest(
            UUID.randomUUID(),
            TransactionType.INCOME,
            null,
            "miss field",
            "without transactionAt",
            BigDecimal.TWO,
            Set.of("without"));
    Assertions.assertNotNull(request.transactionAt());
  }

  @Test
  public void transactionCreateRequestWithoutTags() {
    var request =
        new TransactionCreateRequest(
            UUID.randomUUID(),
            TransactionType.INCOME,
            Instant.now(),
            "miss field",
            "without transactionAt",
            BigDecimal.TWO);
    Assertions.assertNotNull(request.transactionAt());
  }

  @Test
  public void basedOnAnotherOneWithoutAccountTransactionRequest() {
    var request =
        new TransactionCreateRequest(
            null,
            TransactionType.INCOME,
            Instant.now(),
            "miss field",
            "without transactionAt",
            BigDecimal.TWO,
            Set.of("without"));
    var anotherOne = new TransactionCreateRequest(UUID.randomUUID(), request);
    Assertions.assertNotNull(anotherOne.account());
  }
}
