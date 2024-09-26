package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import com.buddycash.ironbank.infra.events.DataEventType;
import com.buddycash.ironbank.infra.events.TransactionEventProducer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRemoveService implements ITransactionRemoveService {
  private final TransactionRepository transactionRepository;
  private final TransactionEventProducer transactionEventProducer;
  private final TransactionMapper transactionMapper;

  @Autowired
  public TransactionRemoveService(
      TransactionRepository transactionRepository,
      TransactionEventProducer transactionEventProducer,
      TransactionMapper transactionMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionEventProducer = transactionEventProducer;
    this.transactionMapper = transactionMapper;
  }

  @Override
  public Optional<TransactionResponse> remove(UUID accountId, UUID id) {
    var transaction =
        transactionRepository.findByAccountAndId(accountId, id).map(transactionMapper::parse);
    transaction.ifPresent(
        t -> {
          transactionRepository.deleteById(t.id());
          transactionEventProducer.publish(DataEventType.DELETE, t);
        });
    return transaction;
  }
}
