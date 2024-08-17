package com.buddycash.ironbank.server.controllers;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.services.AccountService;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public Optional<AccountResponse> findCurrentAccount(@RequestHeader("x-account-id") UUID accountId) {
        var account = accountService.findById(accountId);
        return account;
    }

    @PostMapping
    public AccountResponse add(@RequestBody AccountCreateRequest accountCreateRequest) {
        var account = accountService.create(accountCreateRequest);
        return account;
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void remove(@RequestHeader("x-account-id") UUID accountId) {
        accountService.remove(accountId);
    }
}
