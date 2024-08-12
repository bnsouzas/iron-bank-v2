package com.buddycash.ironbank.tools;

import com.buddycash.ironbank.domain.transactions.data.TransactionCreate;
import com.buddycash.ironbank.domain.transactions.data.TransactionType;
import com.buddycash.ironbank.domain.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class DataGeneratorService {
    @Autowired
    private TransactionService transactionService;

    private TransactionCreate makeTransaction(UUID accountId, TransactionType type, String name, double amount, Set<String> tags) {
        return new TransactionCreate(accountId, type, Instant.now(), name, String.format("Description of %1s", name), BigDecimal.valueOf(amount), tags);
    }

    private void generateTransactions(UUID accountId) {
        this.transactionService.create(accountId, makeTransaction(accountId, TransactionType.INCOME, "Salary", 100.0, Set.of("salary")));
        this.transactionService.create(accountId, makeTransaction(accountId, TransactionType.OUTCOME, "Water", 10.33, Set.of("housing", "bill")));
        this.transactionService.create(accountId, makeTransaction(accountId, TransactionType.OUTCOME, "Eletricity", 15.59, Set.of("housing", "bill")));
        this.transactionService.create(accountId, makeTransaction(accountId, TransactionType.OUTCOME, "Rent of month", 60.5, Set.of("housing", "rent")));
    }

    public UUID generateAccount() {
        var accountId = UUID.randomUUID();
        this.generateTransactions(accountId);
        return accountId;
    }
}
