package com.buddycash.ironbank.domain.accounts.data;

import java.util.Objects;

public record AccountCreateRequest(String name) {
    public AccountCreateRequest {
        if (Objects.isNull(name))
            throw new IllegalArgumentException("Property name are required");
    }
}
