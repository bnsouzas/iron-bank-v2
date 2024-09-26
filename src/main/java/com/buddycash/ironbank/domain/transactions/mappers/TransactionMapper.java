package com.buddycash.ironbank.domain.transactions.mappers;

import com.buddycash.ironbank.domain.currencies.ICurrencyAggregatorService;
import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionExtraInfo;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import com.buddycash.ironbank.domain.transactions.models.Transaction;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
  private static final Set<String> defaultCurrencies = Set.of("USD", "EUR", "GBP", "ARRS");
  private final ICurrencyAggregatorService currencyAggregatorService;

  @Autowired
  public TransactionMapper(ICurrencyAggregatorService currencyAggregatorService) {
    this.currencyAggregatorService = currencyAggregatorService;
  }

  public TransactionResponse parse(Transaction transaction) {
    var aggregatorRequest =
        new CurrencyAggregatorRequest(
            "BRL", defaultCurrencies, transaction.getPrice(), transaction.getTransactionAt());
    var otherCurrencies = this.currencyAggregatorService.aggregate(aggregatorRequest);
    var extra = new TransactionExtraInfo(otherCurrencies);
    var tagsResponse =
        transaction.getTags().stream().map(TagMapper::parse).collect(Collectors.toSet());
    return new TransactionResponse(
        transaction.getId(),
        transaction.getAccount(),
        transaction.getType(),
        transaction.getTransactionAt(),
        transaction.getName(),
        transaction.getDescription(),
        transaction.getPrice(),
        tagsResponse,
        extra);
  }

  public Transaction parse(UUID accountId, TransactionCreateRequest transactionCreateRequest) {
    var transaction = new TransactionCreateRequest(accountId, transactionCreateRequest);
    return this.parse(transaction);
  }

  public Transaction parse(TransactionCreateRequest transactionCreate) {
    var tags =
        transactionCreate.tags().stream()
            .map(t -> new Tag(transactionCreate.account(), t))
            .collect(Collectors.toSet());
    var transaction = new Transaction();
    transaction.setAccount(transactionCreate.account());
    transaction.setType(transactionCreate.type());
    transaction.setTransactionAt(transactionCreate.transactionAt());
    transaction.setName(transactionCreate.name());
    transaction.setDescription(transactionCreate.description());
    transaction.setPrice(transactionCreate.price());
    transaction.setTags(tags);
    return transaction;
  }

  public Transaction parse(TransactionResponse transactionResponse) {
    var transaction = new Transaction();
    transaction.setId(transactionResponse.id());
    transaction.setAccount(transactionResponse.account());
    transaction.setType(transactionResponse.type());
    transaction.setTransactionAt(transactionResponse.transactionAt());
    transaction.setName(transactionResponse.name());
    transaction.setDescription(transactionResponse.description());
    transaction.setPrice(transactionResponse.price());

    var t = transactionResponse.tags().stream().map(TagMapper::parse).collect(Collectors.toSet());
    transaction.getTags().addAll(t);

    return transaction;
  }
}
