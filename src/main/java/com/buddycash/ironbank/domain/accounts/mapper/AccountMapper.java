package com.buddycash.ironbank.domain.accounts.mapper;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.model.Account;

public abstract class AccountMapper {
    public static Account parse(AccountCreateRequest accountCreate) {
        var account = new Account();
        account.setName(accountCreate.name());
        return account;
    }

    public static AccountResponse parse(Account account) {
        return new AccountResponse(account.getId(), account.getName());
    }
}
