package com.buddycash.ironbank.domain.accounts.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountCreateServiceTests extends BaseApplicationTest {

  @Autowired private AccountService accountService;

  @Test
  public void createAccountServiceTest() {
    var accountCreate = new AccountCreateRequest("Principal");
    var created = accountService.create(accountCreate);
    Assertions.assertNotNull(created.id());
    Assertions.assertEquals(accountCreate.name(), created.name());
  }
}
