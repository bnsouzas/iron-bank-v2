package com.buddycash.ironbank.transactions.services.crud;

import com.buddycash.ironbank.transactions.data.TransactionResponse;
import com.buddycash.ironbank.transactions.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionRemoveService implements ITransactionRemoveService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Optional<TransactionResponse> remove(UUID accountId, UUID id) {
        var transaction = transactionRepository.findByAccountAndId(accountId, id)
                .map(t -> modelMapper.map(t, TransactionResponse.class));
        transaction.ifPresent(t -> transactionRepository.deleteById(t.getId()));
        return transaction;
    }
}
