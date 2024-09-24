package com.buddycash.ironbank.domain.transactions.data;

import com.buddycash.ironbank.domain.currencies.data.CurrencyConvertResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
public record TransactionResponse(UUID id, UUID account, TransactionType type, Instant transactionAt, String name, String description, BigDecimal price, Set<TagResponse> tags, TransactionExtraInfo extraInfo) {
    public TransactionResponse(UUID id, UUID account, TransactionType type, Instant transactionAt, String name, String description, BigDecimal price, Set<TagResponse> tags) {
        this(id, account, type, transactionAt, name, description, price, tags, new TransactionExtraInfo(new ArrayList<>()));
    }
}
