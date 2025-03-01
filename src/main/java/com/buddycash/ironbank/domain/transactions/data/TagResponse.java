package com.buddycash.ironbank.domain.transactions.data;

import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public record TagResponse(UUID id, UUID account, @Field(type = FieldType.Text, analyzer = "rebuilt_brazilian") String name) {}
