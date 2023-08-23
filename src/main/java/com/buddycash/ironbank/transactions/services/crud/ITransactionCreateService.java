package com.buddycash.ironbank.transactions.services.crud;

import com.buddycash.ironbank.transactions.data.TransactionCreate;
import com.buddycash.ironbank.transactions.data.TransactionResponse;

public interface ITransactionCreateService {
    TransactionResponse create(TransactionCreate transactionToCreate);
}
