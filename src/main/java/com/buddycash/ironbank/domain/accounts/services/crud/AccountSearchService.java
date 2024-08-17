package com.buddycash.ironbank.domain.accounts.services.crud;

import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.mapper.AccountMapper;
import com.buddycash.ironbank.domain.accounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountSearchService implements IAccountSearchService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<AccountResponse> findById(UUID accountId) {
        var account = accountRepository.findById(accountId);
        var accountResponse = account.map(AccountMapper::parse);
        return accountResponse;
    }
}
