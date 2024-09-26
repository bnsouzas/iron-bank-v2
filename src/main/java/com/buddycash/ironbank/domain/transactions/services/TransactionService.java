package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionCreateService;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionRemoveService;
import com.buddycash.ironbank.domain.transactions.services.crud.ITransactionSearchService;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService
    implements ITransactionSearchService, ITransactionCreateService, ITransactionRemoveService {
  private ITransactionCreateService transactionCreateService;
  private ITransactionSearchService transactionSearchService;
  private ITransactionRemoveService transactionRemoveService;

  @Autowired
  public TransactionService(
      ITransactionCreateService transactionCreateService,
      ITransactionSearchService transactionSearchService,
      ITransactionRemoveService transactionRemoveService) {
    this.transactionCreateService = transactionCreateService;
    this.transactionSearchService = transactionSearchService;
    this.transactionRemoveService = transactionRemoveService;
  }

  @Override
  public TransactionResponse create(UUID accountId, TransactionCreateRequest transactionToCreate) {
    return transactionCreateService.create(accountId, transactionToCreate);
  }

  @Override
  public Collection<TransactionResponse> find(UUID accountId) {
    return transactionSearchService.find(accountId);
  }

  @Override
  public Optional<TransactionResponse> findById(UUID accountId, UUID id) {
    return transactionSearchService.findById(accountId, id);
  }

  public Optional<TransactionResponse> remove(UUID accountId, UUID id) {
    return transactionRemoveService.remove(accountId, id);
  }
}
