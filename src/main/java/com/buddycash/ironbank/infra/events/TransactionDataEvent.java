package com.buddycash.ironbank.infra.events;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import java.time.Instant;

public record TransactionDataEvent(
    DataEventType type, Instant eventTime, TransactionResponse data) {
  public TransactionDataEvent(DataEventType type, TransactionResponse data) {
    this(type, Instant.now(), data);
  }
}
