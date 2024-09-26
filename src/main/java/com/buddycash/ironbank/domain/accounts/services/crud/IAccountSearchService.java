package com.buddycash.ironbank.domain.accounts.services.crud;

import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import java.util.Optional;
import java.util.UUID;

public interface IAccountSearchService {
  Optional<AccountResponse> findById(UUID accountId);
}
