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

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Collection<TransactionResponse> find(UUID accountId) {
        var transactions = transactionRepository.findAllByAccount(accountId);
        var response = transactions.stream()
                .map(TransactionMapper::parse)
                .toList();
        return response;
    }

    @Override
    public Optional<TransactionResponse> findById(UUID accountId, UUID id) {
        var transaction = transactionRepository.findByAccountAndId(accountId, id);
        var response = transaction.map(TransactionMapper::parse);
        return response;
    }
}
