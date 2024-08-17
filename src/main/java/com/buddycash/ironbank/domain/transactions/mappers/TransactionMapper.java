package com.buddycash.ironbank.domain.transactions.mappers;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import com.buddycash.ironbank.domain.transactions.models.Transaction;

import java.util.UUID;
import java.util.stream.Collectors;

public abstract class TransactionMapper {
    public static TransactionResponse parse(Transaction transaction) {
        var tagsResponse = transaction.getTags().stream().map(TagMapper::parse).collect(Collectors.toSet());
        return new TransactionResponse(
                transaction.getId(),
                transaction.getAccount(),
                transaction.getType(),
                transaction.getTransactionAt(),
                transaction.getName(),
                transaction.getDescription(),
                transaction.getPrice(),
                tagsResponse);
    }

    public static Transaction parse(UUID accountId, TransactionCreateRequest transactionCreateRequest) {
        var transaction = new TransactionCreateRequest(accountId, transactionCreateRequest);
        return TransactionMapper.parse(transaction);
    }

    public static Transaction parse(TransactionCreateRequest transactionCreate) {
        var tags = transactionCreate.tags().stream().map(t -> new Tag(transactionCreate.account(), t)).collect(Collectors.toSet());
        var transaction = new Transaction();
        transaction.setAccount(transactionCreate.account());
        transaction.setType(transactionCreate.type());
        transaction.setTransactionAt(transactionCreate.transactionAt());
        transaction.setName(transactionCreate.name());
        transaction.setDescription(transactionCreate.description());
        transaction.setPrice(transactionCreate.price());
        transaction.setTags(tags);
        return transaction;
    }

    public static Transaction parse(TransactionResponse transactionResponse) {
        var tags = transactionResponse.tags().stream().map(TagMapper::parse).collect(Collectors.toSet());
        var transaction = new Transaction();
        transaction.setId(transactionResponse.id());
        transaction.setAccount(transactionResponse.account());
        transaction.setType(transactionResponse.type());
        transaction.setTransactionAt(transactionResponse.transactionAt());
        transaction.setName(transactionResponse.name());
        transaction.setDescription(transactionResponse.description());
        transaction.setPrice(transactionResponse.price());
        transaction.getTags().addAll(tags);
        return transaction;
    }
}
