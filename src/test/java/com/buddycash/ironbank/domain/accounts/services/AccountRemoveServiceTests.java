package com.buddycash.ironbank.domain.accounts.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.currencies.AwesomeCurrencyService;
import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TagRepository;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import com.buddycash.ironbank.domain.transactions.services.TransactionService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionCreateService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionRemoveService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionSearchService;
import com.buddycash.ironbank.infra.events.TransactionEventProducer;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountRemoveServiceTests extends BaseApplicationTest {
    private AccountService accountService;
    private DataGeneratorService dataGeneratorService;
    private IAwesomeExchangePricesClient awesomeExchangePricesClient;

    @BeforeEach
    void init(
            @Mock IAwesomeExchangePricesClient awesomeExchangePricesClient,
            @Autowired AccountService accountService,
            @Autowired TransactionRepository transactionRepository,
            @Autowired TagRepository tagRepository,
            @Autowired TransactionEventProducer transactionEventProducer) {
        this.awesomeExchangePricesClient = awesomeExchangePricesClient;
        var awesomeCurrencyService = new AwesomeCurrencyService(this.awesomeExchangePricesClient);
        var transactionMapper = new TransactionMapper(awesomeCurrencyService);
        var transactionSearchService = new TransactionSearchService(transactionRepository, transactionMapper);
        var transactionCreateService = new TransactionCreateService(transactionRepository, tagRepository, transactionEventProducer, transactionMapper);
        var transactionRemoveService = new TransactionRemoveService(transactionRepository, transactionEventProducer, transactionMapper);
        var transactionService = new TransactionService(transactionCreateService, transactionSearchService, transactionRemoveService);
        this.dataGeneratorService = new DataGeneratorService(transactionService, accountService);
        this.accountService = accountService;
    }

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
