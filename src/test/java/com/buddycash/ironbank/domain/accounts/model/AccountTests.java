package com.buddycash.ironbank.domain.accounts.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class AccountTests {
    private Account deepClone(Account account) {
        var clone = new Account();
        clone.setId(account.getId());
        clone.setName(account.getName());
        return clone;
    }

    @Test
    public void equalsTests() {
        var account1 = new Account();
        account1.setId(UUID.randomUUID());
        account1.setName("Unique name");
        var account2 = deepClone(account1);

        Assertions.assertEquals(account1, account2);
    }

    @Test
    public void hashCodeTest() {
        var account1 = new Account();
        account1.setId(UUID.randomUUID());
        account1.setName("Unique name");
        var account2 = deepClone(account1);

        Assertions.assertEquals(account1.hashCode(), account2.hashCode());
    }
}
