package com.buddycash.ironbank.server.controllers;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.services.AccountService;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired private AccountService accountService;

  @GetMapping
  public Optional<AccountResponse> findCurrentAccount(
      @RequestHeader("x-account-id") UUID accountId) {
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
