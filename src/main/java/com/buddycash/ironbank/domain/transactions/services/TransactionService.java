package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionCreateService;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionRemoveService;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService implements ITransactionSearchService, ITransactionCreateService, ITransactionRemoveService {
    @Autowired
    private ITransactionCreateService transactionCreateService;

    @Autowired
    private ITransactionSearchService transactionSearchService;

    @Autowired
    private ITransactionRemoveService transactionRemoveService;

    @Override
    public TransactionResponse create(UUID accountId, TransactionCreateRequest transactionToCreate) {
        return transactionCreateService.create(accountId, transactionToCreate);
    }

    @Override
    public Collection<TransactionResponse> find(UUID accountId) {
        return transactionSearchService.find(accountId);
    }

    @Override
    public Optional<TransactionResponse> findById(UUID accountId, UUID id) {
        return transactionSearchService.findById(accountId, id);
    }

    public Optional<TransactionResponse> remove(UUID accountId, UUID id) {
        return transactionRemoveService.remove(accountId, id);
    }
}
