package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.currencies.AwesomeCurrencyService;
import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionType;
import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.repositories.TagRepository;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionRepository;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionCreateService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionRemoveService;
import com.buddycash.ironbank.domain.transactions.services.crud.TransactionSearchService;
import com.buddycash.ironbank.infra.events.TransactionEventProducer;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionCreateServiceTests extends BaseApplicationTest {
  private final UUID accountId = UUID.randomUUID();

  private IAwesomeExchangePricesClient awesomeExchangePricesClient;
  private TransactionService transactionService;

  @BeforeEach
  void init(
      @Mock IAwesomeExchangePricesClient awesomeExchangePricesClient,
      @Autowired TransactionRepository transactionRepository,
      @Autowired TagRepository tagRepository,
      @Autowired TransactionEventProducer transactionEventProducer) {
    this.awesomeExchangePricesClient = awesomeExchangePricesClient;
    var awesomeCurrencyService = new AwesomeCurrencyService(this.awesomeExchangePricesClient);
    var transactionMapper = new TransactionMapper(awesomeCurrencyService);
    var transactionSearchService =
        new TransactionSearchService(transactionRepository, transactionMapper);
    var transactionCreateService =
        new TransactionCreateService(
            transactionRepository, tagRepository, transactionEventProducer, transactionMapper);
    var transactionRemoveService =
        new TransactionRemoveService(
            transactionRepository, transactionEventProducer, transactionMapper);
    this.transactionService =
        new TransactionService(
            transactionCreateService, transactionSearchService, transactionRemoveService);
  }

  @Test
  void createNewOutcomeTransaction() {
    var tags = new HashSet<String>();
    tags.add("housing");
    tags.add("bill");
    var transactionCreate =
        new TransactionCreateRequest(
            accountId,
            TransactionType.OUTCOME,
            Instant.now(),
            "Water",
            "Water Bill",
            BigDecimal.TEN,
            tags);
    var transactionResponse = this.transactionService.create(accountId, transactionCreate);
    Assertions.assertNotNull(transactionResponse);
    Assertions.assertNotNull(transactionResponse.id());
    Assertions.assertEquals(TransactionType.OUTCOME, transactionResponse.type());
    Assertions.assertEquals(2, transactionResponse.tags().size());
  }

  @Test
  void createNewOutcomeTransactionWithNullAccountId() {
    var tags = new HashSet<String>();
    tags.add("housing");
    tags.add("bill");
    var transactionCreate =
        new TransactionCreateRequest(
            null,
            TransactionType.OUTCOME,
            Instant.now(),
            "Water",
            "Water Bill",
            BigDecimal.TEN,
            tags);
    var transactionResponse = this.transactionService.create(accountId, transactionCreate);
    Assertions.assertNotNull(transactionResponse);
    Assertions.assertNotNull(transactionResponse.id());
    Assertions.assertEquals(TransactionType.OUTCOME, transactionResponse.type());
    Assertions.assertEquals(2, transactionResponse.tags().size());
  }

  @Test
  void createNewOutcomeTransactionWithoutTags() {
    var transactionCreate =
        new TransactionCreateRequest(
            accountId,
            TransactionType.OUTCOME,
            Instant.now(),
            "Water",
            "Water Bill",
            BigDecimal.TEN);
    var transactionResponse = this.transactionService.create(accountId, transactionCreate);
    Assertions.assertNotNull(transactionResponse);
    Assertions.assertNotNull(transactionResponse.id());
    Assertions.assertEquals(TransactionType.OUTCOME, transactionResponse.type());
    Assertions.assertEquals(0, transactionResponse.tags().size());
  }

  @Test
  void createNewIncomeTransaction() {
    var tags = new HashSet<String>();
    tags.add("salary");
    var transactionCreate =
        new TransactionCreateRequest(
            accountId,
            TransactionType.INCOME,
            Instant.now(),
            "Salary",
            "Monthly revenue",
            BigDecimal.valueOf(100),
            tags);
    var transactionResponse = this.transactionService.create(accountId, transactionCreate);
    Assertions.assertNotNull(transactionResponse);
    Assertions.assertNotNull(transactionResponse.id());
    Assertions.assertEquals(TransactionType.INCOME, transactionResponse.type());
    Assertions.assertEquals(1, transactionResponse.tags().size());
  }
}
