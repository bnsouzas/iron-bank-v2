package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TransactionSearchServiceTests extends BaseApplicationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DataGeneratorService dataGeneratorService;

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
