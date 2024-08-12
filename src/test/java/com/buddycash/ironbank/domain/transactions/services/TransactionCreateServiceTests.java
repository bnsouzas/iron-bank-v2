package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreate;
import com.buddycash.ironbank.domain.transactions.data.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

@SpringBootTest
public class TransactionCreateServiceTests extends BaseApplicationTest {
    private final UUID accountId = UUID.randomUUID();

    @Autowired
    private TransactionService transactionService;

    @Test
    void createNewOutcomeTransaction() {
        var tags = new HashSet<String>();
        tags.add("housing");
        tags.add("bill");
        var transactionCreate = new TransactionCreate(accountId, TransactionType.OUTCOME, Instant.now(), "Water", "Water Bill", BigDecimal.TEN, tags);
        var transactionResponse = this.transactionService.create(accountId, transactionCreate);
        Assertions.assertNotNull(transactionResponse);
        Assertions.assertNotNull(transactionResponse.id());
        Assertions.assertEquals(TransactionType.OUTCOME, transactionResponse.type());
        Assertions.assertEquals(2, transactionResponse.tags().size());
    }

    @Test
    void createNewOutcomeTransactionWithoutTags() {
        var transactionCreate = new TransactionCreate(accountId, TransactionType.OUTCOME, Instant.now(), "Water", "Water Bill", BigDecimal.TEN);
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
        var transactionCreate = new TransactionCreate(accountId, TransactionType.INCOME, Instant.now(), "Salary", "Monthly revenue", BigDecimal.valueOf(100), tags);
        var transactionResponse = this.transactionService.create(accountId, transactionCreate);
        Assertions.assertNotNull(transactionResponse);
        Assertions.assertNotNull(transactionResponse.id());
        Assertions.assertEquals(TransactionType.INCOME, transactionResponse.type());
        Assertions.assertEquals(1, transactionResponse.tags().size());
    }
}
