package com.buddycash.ironbank.domain.transactions.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record TransactionCreateRequest(UUID account, TransactionType type, Instant transactionAt, String name, String description, BigDecimal price, Set<String> tags) {
    public TransactionCreateRequest {
        if (Objects.isNull(transactionAt)) {
            transactionAt = Instant.now();
        }
    }
    public TransactionCreateRequest(UUID account, TransactionCreateRequest original) {
        this(account, original.type, original.transactionAt, original.name, original.description, original.price, original.tags);
    }
    public TransactionCreateRequest(UUID account, TransactionType type, Instant transactionAt, String name, String description, BigDecimal price) {
        this(account, type, transactionAt, name, description, price, Set.of());
    }
}