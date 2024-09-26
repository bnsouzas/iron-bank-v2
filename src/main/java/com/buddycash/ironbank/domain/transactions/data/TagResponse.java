package com.buddycash.ironbank.domain.transactions.data;

import java.util.UUID;

public record TagResponse(UUID id, UUID account, String name) {}
