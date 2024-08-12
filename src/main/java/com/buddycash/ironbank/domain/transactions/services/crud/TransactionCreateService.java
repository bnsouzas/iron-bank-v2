package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreate;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import com.buddycash.ironbank.domain.transactions.repositories.TagRepository;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionCreateService implements ITransactionCreateService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    public TransactionResponse create(UUID accountId, TransactionCreate transactionToCreate) {
        var transaction = TransactionMapper.parse(accountId, transactionToCreate);
        transaction.getTags().clear();
        transaction.getTags().addAll(
                transactionToCreate.tags().stream().map((tagName) -> {
                    var tagReference = tagRepository.findByAccountAndName(transactionToCreate.account(), tagName);
                    if (tagReference.isPresent()) return tagReference.get();
                    var tag = new Tag(transactionToCreate.account(), tagName);
                    return tagRepository.save(tag);
                }).collect(Collectors.toSet()));
        var saved = transactionRepository.save(transaction);
        return TransactionMapper.parse(saved);
    }
}
