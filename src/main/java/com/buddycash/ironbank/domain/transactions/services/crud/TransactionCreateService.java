package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import com.buddycash.ironbank.domain.transactions.repositories.TagRepository;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import com.buddycash.ironbank.infra.events.DataEventType;
import com.buddycash.ironbank.infra.events.TransactionEventProducer;
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

    @Autowired
    private TransactionEventProducer transactionEventProducer;

    public TransactionResponse create(UUID accountId, TransactionCreateRequest transactionToCreate) {
        var response = this.persist(accountId, transactionToCreate);
        this.transactionEventProducer.publish(DataEventType.CREATE, response);
        return response;
    }

    @Transactional
    private TransactionResponse persist(UUID accountId, TransactionCreateRequest transactionToCreate) {
        var transaction = TransactionMapper.parse(accountId, transactionToCreate);
        transaction.getTags().clear();
        transaction.getTags().addAll(
                transactionToCreate.tags().stream().map((tagName) -> {
                    var tagReference = tagRepository.findByAccountAndName(transactionToCreate.account(), tagName);
                    if (tagReference.isPresent()) return tagReference.get();
                    var tag = new Tag(transaction.getAccount(), tagName);
                    return tagRepository.save(tag);
                }).collect(Collectors.toSet()));
        var saved = transactionRepository.save(transaction);
        var response = TransactionMapper.parse(saved);
        return response;
    }
}
