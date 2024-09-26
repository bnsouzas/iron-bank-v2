package com.buddycash.ironbank.domain.accounts.services.crud;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.mapper.AccountMapper;
import com.buddycash.ironbank.domain.accounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreateService implements IAccountCreateService {

  @Autowired private AccountRepository accountRepository;

  @Override
  public AccountResponse create(AccountCreateRequest accountCreate) {
    var account = AccountMapper.parse(accountCreate);
    var saved = accountRepository.save(account);
    return AccountMapper.parse(saved);
  }
}
