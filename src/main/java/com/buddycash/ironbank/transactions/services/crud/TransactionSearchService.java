package com.buddycash.ironbank.transactions.services.crud;

import com.buddycash.ironbank.transactions.data.TransactionResponse;
import com.buddycash.ironbank.transactions.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionSearchService implements ITransactionSearchService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Collection<TransactionResponse> find(UUID accountId) {
        var transactions = transactionRepository.findAllByAccount(accountId);
        var response = transactions.stream()
                .map(t -> modelMapper.map(t, TransactionResponse.class))
                .toList();
        return response;
    }

    @Override
    public Optional<TransactionResponse> findById(UUID accountId, UUID id) {
        var transaction = transactionRepository.findByAccountAndId(accountId, id);
        var response = transaction.map(t -> modelMapper.map(t, TransactionResponse.class));
        return response;
    }
}
