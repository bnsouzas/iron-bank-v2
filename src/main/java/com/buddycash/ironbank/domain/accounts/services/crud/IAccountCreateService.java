package com.buddycash.ironbank.domain.accounts.services.crud;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;

public interface IAccountCreateService {
  AccountResponse create(AccountCreateRequest accountCreate);
}
