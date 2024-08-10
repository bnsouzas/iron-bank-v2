package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreate;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;

import java.util.UUID;

public interface ITransactionCreateService {
    TransactionResponse create(UUID accountId, TransactionCreate transactionToCreate);
}
