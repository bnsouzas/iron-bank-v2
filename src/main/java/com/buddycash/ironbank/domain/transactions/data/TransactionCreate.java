package com.buddycash.ironbank.domain.transactions.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record TransactionCreate (UUID account, TransactionType type, Instant transactionAt, String name, String description, BigDecimal price, Set<String> tags) {
    public TransactionCreate(UUID account, TransactionCreate original) {
        this(account, original.type, original.transactionAt, original.name, original.description, original.price, original.tags);
    }
}