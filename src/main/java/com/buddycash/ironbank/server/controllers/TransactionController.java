package com.buddycash.ironbank.server.controllers;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.services.TransactionService;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

  @Autowired private TransactionService transactionService;

  @GetMapping
  public Collection<TransactionResponse> list(@RequestHeader("x-account-id") UUID accountId) {
    var transactions = transactionService.find(accountId);
    return transactions;
  }

  @PostMapping
  public TransactionResponse add(
      @RequestBody TransactionCreateRequest transactionCreateRequest,
      @RequestHeader("x-account-id") UUID accountId) {
    var transaction = transactionService.create(accountId, transactionCreateRequest);
    return transaction;
  }

  @GetMapping("/{id}")
  public Optional<TransactionResponse> findOne(
      @PathVariable UUID id, @RequestHeader("x-account-id") UUID accountId) {
    var transaction = transactionService.findById(accountId, id);
    return transaction;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void remove(@PathVariable UUID id, @RequestHeader("x-account-id") UUID accountId) {
    transactionService.remove(accountId, id);
  }
}
