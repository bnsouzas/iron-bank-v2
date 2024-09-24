package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionSearchService implements ITransactionSearchService {

    private TransactionRepository transactionRepository;

    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionSearchService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Collection<TransactionResponse> find(UUID accountId) {
        var transactions = transactionRepository.findAllByAccount(accountId);
        var response = transactions.stream()
                .map(transactionMapper::parse)
                .toList();
        return response;
    }

    @Override
    public Optional<TransactionResponse> findById(UUID accountId, UUID id) {
        var transaction = transactionRepository.findByAccountAndId(accountId, id);
        var response = transaction.map(transactionMapper::parse);
        return response;
    }
}
