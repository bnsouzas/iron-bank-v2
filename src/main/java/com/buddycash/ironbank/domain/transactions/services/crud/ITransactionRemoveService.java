package com.buddycash.ironbank.domain.transactions.services.crud;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import java.util.Optional;
import java.util.UUID;

public interface ITransactionRemoveService {
  Optional<TransactionResponse> remove(UUID accountId, UUID id);
}
