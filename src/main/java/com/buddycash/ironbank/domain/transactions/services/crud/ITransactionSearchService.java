package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionSearchService {
  Collection<TransactionResponse> find(UUID accountId);

  Optional<TransactionResponse> findById(UUID accountId, UUID id);
}
