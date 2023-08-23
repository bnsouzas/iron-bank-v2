package com.buddycash.ironbank.transactions.services.crud;

import com.buddycash.ironbank.transactions.data.TransactionCreate;
import com.buddycash.ironbank.transactions.data.TransactionResponse;
import com.buddycash.ironbank.transactions.models.Tag;
import com.buddycash.ironbank.transactions.models.Transaction;
import com.buddycash.ironbank.transactions.repositories.TagRepository;
import com.buddycash.ironbank.transactions.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class TransactionCreateService implements ITransactionCreateService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TagRepository tagRepository;

    public TransactionResponse create(TransactionCreate transactionToCreate) {
        var transaction = modelMapper.map(transactionToCreate, Transaction.class);
        var tagsSet = new HashSet<>(transactionToCreate.getTags());
        transaction.getTags().clear();
        transaction.getTags().addAll(
                tagsSet.stream().map((tagName) -> {
                    var tagReference = tagRepository.findByAccountAndName(transactionToCreate.getAccount(), tagName);
                    if (tagReference.isPresent()) return tagReference.get();
                    var tag = new Tag(transactionToCreate.getAccount(), tagName);
                    return tagRepository.save(tag);
                }).collect(Collectors.toSet()));
        var saved = transactionRepository.save(transaction);
        var response = modelMapper.map(saved, TransactionResponse.class);
        return response;
    }
}
