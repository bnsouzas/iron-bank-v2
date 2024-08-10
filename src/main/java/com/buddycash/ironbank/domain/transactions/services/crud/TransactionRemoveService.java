package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionRemoveService implements ITransactionRemoveService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionResponse> remove(UUID accountId, UUID id) {
        var transaction = transactionRepository.findByAccountAndId(accountId, id)
                .map(TransactionMapper::parse);
        transaction.ifPresent(t -> transactionRepository.deleteById(t.id()));
        return transaction;
    }
}
