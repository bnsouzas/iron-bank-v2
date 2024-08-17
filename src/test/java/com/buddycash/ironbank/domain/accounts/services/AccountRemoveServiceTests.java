package com.buddycash.ironbank.domain.accounts.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class AccountRemoveServiceTests extends BaseApplicationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Test
    public void removeAccountServiceTest() {
        var account = dataGeneratorService.generateAccount();
        var accountFounded = accountService.findById(account.id());
        Assertions.assertTrue(accountFounded.isPresent());

        var removed = accountService.remove(accountFounded.get().id());
        Assertions.assertTrue(removed.isPresent());

        var accountNotFounded = accountService.findById(account.id());
        Assertions.assertFalse(accountNotFounded.isPresent());
    }

    @Test
    public void removeAccountNotFoundServiceTest() {
        var removed = accountService.remove(UUID.randomUUID());
        Assertions.assertTrue(removed.isEmpty());
    }
}
