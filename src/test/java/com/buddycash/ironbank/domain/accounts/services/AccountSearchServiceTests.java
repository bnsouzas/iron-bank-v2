package com.buddycash.ironbank.domain.accounts.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class AccountSearchServiceTests extends BaseApplicationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Test
    public void accountFindByIdFoundServiceTest() {
        var account = dataGeneratorService.generateAccount();
        var accountFounded = accountService.findById(account.id());
        Assertions.assertTrue(accountFounded.isPresent());
    }

    @Test
    public void accountFindByIdNotFoundServiceTest() {
        var account = dataGeneratorService.generateAccount();
        var accountFounded = accountService.findById(UUID.randomUUID());
        Assertions.assertTrue(accountFounded.isEmpty());
    }
}
