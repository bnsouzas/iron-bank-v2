package com.buddycash.ironbank.server.controllers;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreate;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public Collection<TransactionResponse> list(@RequestHeader("x-account-id") UUID accountId) {
        var transactions = transactionService.find(accountId);
        return transactions;
    }

    @PostMapping
    public TransactionResponse add(@RequestBody TransactionCreate transactionCreate, @RequestHeader("x-account-id") UUID accountId) {
        var transaction = transactionService.create(accountId, transactionCreate);
        return transaction;
    }

    @GetMapping("/{id}")
    public Optional<TransactionResponse> findOne(@PathVariable UUID id, @RequestHeader("x-account-id") UUID accountId) {
        var transaction = transactionService.findById(accountId, id);
        return transaction;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id, @RequestHeader("x-account-id") UUID accountId) {
        transactionService.remove(accountId, id);
    }
}