package com.buddycash.ironbank.tools;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import com.buddycash.ironbank.domain.accounts.data.AccountResponse;
import com.buddycash.ironbank.domain.accounts.services.AccountService;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionType;
import com.buddycash.ironbank.domain.transactions.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
public class DataGeneratorService {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public DataGeneratorService(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    private TransactionCreateRequest makeTransaction(UUID accountId, TransactionType type, String name, double amount, Set<String> tags) {
        return new TransactionCreateRequest(accountId, type, Instant.now(), name, String.format("Description of %1s", name), BigDecimal.valueOf(amount), tags);
    }

    private void generateTransactions(AccountResponse account) {
        this.transactionService.create(account.id(), makeTransaction(account.id(), TransactionType.INCOME, "Salary", 100.0, Set.of("salary")));
        this.transactionService.create(account.id(), makeTransaction(account.id(), TransactionType.OUTCOME, "Water", 10.33, Set.of("housing", "bill")));
        this.transactionService.create(account.id(), makeTransaction(account.id(), TransactionType.OUTCOME, "Eletricity", 15.59, Set.of("housing", "bill")));
        this.transactionService.create(account.id(), makeTransaction(account.id(), TransactionType.OUTCOME, "Rent of month", 60.5, Set.of("housing", "rent")));
    }

    public AccountResponse generateAccount() {
        var accountToCreate = new AccountCreateRequest("generated-account");
        var account = this.accountService.create(accountToCreate);
        this.generateTransactions(account);
        return account;
    }
}
