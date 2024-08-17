package com.buddycash.ironbank.domain.accounts.services.crud;

import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.mapper.AccountMapper;
import com.buddycash.ironbank.domain.accounts.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountRemoveService implements IAccountRemoveService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Optional<AccountResponse> remove(UUID accountId) {
        var account = accountRepository.findById(accountId)
                .map(AccountMapper::parse);
        account.ifPresent(ac -> accountRepository.deleteById(ac.id()));
        return account;
    }
}
