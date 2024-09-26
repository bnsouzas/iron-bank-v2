package com.buddycash.ironbank.domain.accounts.data;

import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountResponseTests {

  @Test
  public void equalsTest() {
    var id = UUID.randomUUID();
    var name = "principal";
    var createAccount1 = new AccountResponse(id, name);
    var createAccount2 = new AccountResponse(id, name);

    Assertions.assertEquals(createAccount1, createAccount2);
  }

  @Test
  public void hashCodeTest() {
    var id = UUID.randomUUID();
    var name = "principal";
    var createAccount1 = new AccountResponse(id, name);
    var createAccount2 = new AccountResponse(id, name);

    Assertions.assertEquals(createAccount1.hashCode(), createAccount2.hashCode());
  }
}
