package com.buddycash.ironbank.domain.transactions.services;

import com.buddycash.ironbank.configuration.BaseApplicationTest;
import com.buddycash.ironbank.tools.DataGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionRemoveServiceTests extends BaseApplicationTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private DataGeneratorService dataGeneratorService;

    @Test
    void removeAllTransactionOfAccount() {
        var accountId = this.dataGeneratorService.generateAccount();
        var transactions = this.transactionService.find(accountId);
        for (var transaction : transactions) {
            this.transactionService.remove(accountId, transaction.id());
        }
        var zeroTransactions = this.transactionService.find(accountId);
        Assertions.assertEquals(0, zeroTransactions.size());
    }
}
