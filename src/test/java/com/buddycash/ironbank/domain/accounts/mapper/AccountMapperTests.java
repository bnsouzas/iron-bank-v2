package com.buddycash.ironbank.domain.accounts.mapper;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.model.Account;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountMapperTests {
  @Test
  public void AccountCreateRequestToAccountParse() {
    var principal = new AccountCreateRequest("principal");
    var account = AccountMapper.parse(principal);
    Assertions.assertNull(account.getId());
    Assertions.assertEquals(principal.name(), account.getName());
  }

  @Test
  public void AccountToAccountResponseParse() {
    var principal = new Account();
    principal.setId(UUID.randomUUID());
    principal.setName("principal");
    var account = AccountMapper.parse(principal);
    Assertions.assertEquals(principal.getId(), account.id());
    Assertions.assertEquals(principal.getName(), account.name());
  }
}
