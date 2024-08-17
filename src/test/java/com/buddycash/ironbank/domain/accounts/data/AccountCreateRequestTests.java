package com.buddycash.ironbank.domain.accounts.data;

import com.buddycash.ironbank.domain.accounts.data.AccountCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountCreateRequestTests {
    @Test
    public void accountCreateRequestFull() {
        var name = "principal";
        var account = new AccountCreateRequest(name);
        Assertions.assertEquals(name, account.name());
    }
    @Test
    public void accountCreateRequestWithNullName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new AccountCreateRequest(null);
        });
    }

    @Test
    public void equalsTest() {
        var name = "principal";
        var createAccount1 = new AccountCreateRequest(name);
        var createAccount2 = new AccountCreateRequest(name);

        Assertions.assertEquals(createAccount1, createAccount2);
    }

    @Test
    public void hashCodeTest() {
        var name = "principal";
        var createAccount1 = new AccountCreateRequest(name);
        var createAccount2 = new AccountCreateRequest(name);

        Assertions.assertEquals(createAccount1.hashCode(), createAccount2.hashCode());
    }
}
