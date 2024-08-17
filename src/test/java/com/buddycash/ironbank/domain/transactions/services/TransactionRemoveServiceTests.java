package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class TransactionRemoveServiceTests extends BaseApplicationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Test
    void removeAllTransactionOfAccount() {
        var account = this.dataGeneratorService.generateAccount();
        var transactions = this.transactionService.find(account.id());
        for (var transaction : transactions) {
            var removed = this.transactionService.remove(account.id(), transaction.id());
            Assertions.assertTrue(removed.isPresent());
        }
        var zeroTransactions = this.transactionService.find(account.id());
        Assertions.assertEquals(0, zeroTransactions.size());
    }

    @Test
    public void removeTransactionNotFoundOfAccount() {
        var account = this.dataGeneratorService.generateAccount();
        var removed = transactionService.remove(account.id(), UUID.randomUUID());
        Assertions.assertTrue(removed.isEmpty());
    }
}
