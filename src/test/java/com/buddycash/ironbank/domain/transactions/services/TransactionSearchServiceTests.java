package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.accounts.services.AccountService;
import com.buddycash.ironbank.domain.currencies.AwesomeCurrencyService;
import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TagRepository;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionCreateService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionRemoveService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionSearchService;
import com.buddycash.ironbank.infra.events.TransactionEventProducer;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionSearchServiceTests extends BaseApplicationTest {
    private IAwesomeExchangePricesClient awesomeExchangePricesClient;
    private DataGeneratorService dataGeneratorService;
    private TransactionService transactionService;

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
        this.transactionService = new TransactionService(transactionCreateService, transactionSearchService, transactionRemoveService);
        this.dataGeneratorService = new DataGeneratorService(transactionService, accountService);
    }

    @Test
    void searchTransactionsOfAccount() {
        var account = this.dataGeneratorService.generateAccount();
        var transactions = this.transactionService.find(account.id());
        Assertions.assertEquals(4, transactions.size());
    }

    @Test
    void searchEachTransactionOfAccount() {
        var account = this.dataGeneratorService.generateAccount();
        var transactions = this.transactionService.find(account.id());
        for (var transaction : transactions) {
            var transactionFound = this.transactionService.findById(account.id(), transaction.id());
            Assertions.assertEquals(transactionFound, transactionFound);
        }
    }

    @Test
    void searchNotFoundTransactionOfAccount() {
        var account = this.dataGeneratorService.generateAccount();
        var transaction = this.transactionService.findById(account.id(), UUID.randomUUID());
        Assertions.assertTrue(transaction.isEmpty());
    }
}
