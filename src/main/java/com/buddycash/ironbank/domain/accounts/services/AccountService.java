package com.buddycash.ironbank.domain.accounts.services;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.services.crud.IAccountCreateService;
import com.buddycash.ironbank.domain.accounts.services.crud.IAccountRemoveService;
import com.buddycash.ironbank.domain.accounts.services.crud.IAccountSearchService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService
    implements IAccountCreateService, IAccountRemoveService, IAccountSearchService {

  @Autowired private IAccountCreateService accountCreateService;

  @Autowired private IAccountSearchService accountSearchService;

  @Autowired IAccountRemoveService accountRemoveService;

  @Override
  public AccountResponse create(AccountCreateRequest accountCreate) {
    return this.accountCreateService.create(accountCreate);
  }

  @Override
  public Optional<AccountResponse> remove(UUID accountId) {
    return accountRemoveService.remove(accountId);
  }

  @Override
  public Optional<AccountResponse> findById(UUID accountId) {
    return accountSearchService.findById(accountId);
  }
}
