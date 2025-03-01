package com.buddycash.ironbank.domain.transactions.data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "transactions")
@Setting(settingPath = "/erp-iron-analyzer.json", shards = 5, replicas = 3)
public record TransactionResponse(
    UUID id,
    UUID account,
    TransactionType type,
    @Field(type = FieldType.Date)
    Instant transactionAt,
    @Field(type = FieldType.Text, analyzer = "rebuilt_brazilian")
    String name,
    @Field(type = FieldType.Text, analyzer = "rebuilt_brazilian")
    String description,
    BigDecimal price,
    Set<TagResponse> tags,
    TransactionExtraInfo extraInfo) {
  public TransactionResponse(
      UUID id,
      UUID account,
      TransactionType type,
      Instant transactionAt,
      String name,
      String description,
      BigDecimal price,
      Set<TagResponse> tags) {
    this(
        id,
        account,
        type,
        transactionAt,
        name,
        description,
        price,
        tags,
        new TransactionExtraInfo(new ArrayList<>()));
  }
}
