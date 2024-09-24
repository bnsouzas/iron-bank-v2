package com.buddycash.ironbank.domain.transactions.mappers;

import com.buddycash.ironbank.domain.currencies.AwesomeCurrencyService;
import com.buddycash.ironbank.domain.currencies.clients.IAwesomeExchangePricesClient;
import com.buddycash.ironbank.domain.currencies.data.CurrencyAggregatorRequest;
import com.buddycash.ironbank.domain.transactions.data.TagResponse;
import com.buddycash.ironbank.domain.transactions.data.TransactionCreateRequest;
import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.data.TransactionType;
import com.buddycash.ironbank.domain.transactions.models.Tag;
import com.buddycash.ironbank.domain.transactions.models.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTests {

    private TransactionMapper transactionMapper;

    @BeforeEach
    void init(@Mock IAwesomeExchangePricesClient awesomeExchangePricesClient) {
        var awesomeCurrencyService = new AwesomeCurrencyService(awesomeExchangePricesClient);
        transactionMapper = new TransactionMapper(awesomeCurrencyService);
    }

    @Test
    void transactionResponseToTransactionModelParseTest() {
        var account = UUID.randomUUID();
        var housing = new TagResponse(null, account, "housing");
        var tags = new HashSet<TagResponse>();
        tags.add(housing);
        var transactionResponse = new TransactionResponse(UUID.randomUUID(), account, TransactionType.OUTCOME, Instant.now(), "Water", "Water Bill", BigDecimal.TEN, tags);
        var transactionModel = transactionMapper.parse(transactionResponse);
        Assertions.assertEquals(transactionResponse.id(), transactionModel.getId());
        Assertions.assertEquals(transactionResponse.account(), transactionModel.getAccount());
        Assertions.assertEquals(transactionResponse.type(), transactionModel.getType());
        Assertions.assertEquals(transactionResponse.transactionAt(), transactionModel.getTransactionAt());
        Assertions.assertEquals(transactionResponse.name(), transactionModel.getName());
        Assertions.assertEquals(transactionResponse.description(), transactionModel.getDescription());
        Assertions.assertEquals(transactionResponse.price(), transactionModel.getPrice());
        Assertions.assertEquals(transactionResponse.tags().size(), transactionModel.getTags().size());
        var tagsName = transactionResponse.tags().stream().map(TagResponse::name).toList();
        for (var tag : transactionModel.getTags()) {
            Assertions.assertEquals(account, tag.getAccount());
            Assertions.assertTrue(tagsName.contains(tag.getName()));
        }
    }

    @Test
    void transactionCreateToTransactionModelParseTest() {
        var account = UUID.randomUUID();
        var tags = new HashSet<String>();
        tags.add("housing");
        tags.add("bill");
        var transactionCreate = new TransactionCreateRequest(UUID.randomUUID(), TransactionType.OUTCOME, Instant.now(), "Water", "Water Bill", BigDecimal.TEN, tags);
        var transactionModel = transactionMapper.parse(account, transactionCreate);
        Assertions.assertNull(transactionModel.getId());
        Assertions.assertEquals(account, transactionModel.getAccount());
        Assertions.assertEquals(transactionCreate.type(), transactionModel.getType());
        Assertions.assertEquals(transactionCreate.transactionAt(), transactionModel.getTransactionAt());
        Assertions.assertEquals(transactionCreate.name(), transactionModel.getName());
        Assertions.assertEquals(transactionCreate.description(), transactionModel.getDescription());
        Assertions.assertEquals(transactionCreate.price(), transactionModel.getPrice());
        Assertions.assertEquals(transactionCreate.tags().size(), transactionModel.getTags().size());
        for (var tag : transactionModel.getTags()) {
            Assertions.assertTrue(transactionCreate.tags().contains(tag.getName()));
        }
    }

    @Test
    void transactionToTransactionResponseParseTest(@Mock IAwesomeExchangePricesClient awesomeExchangePricesClient) {
        lenient().when(awesomeExchangePricesClient.dailyPrice(any(String.class), any(String.class), any(String.class)))
                .thenReturn(new ArrayList<>());
        var transactionId = UUID.randomUUID();
        var account = UUID.randomUUID();
        var transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAccount(account);
        transaction.setType(TransactionType.OUTCOME);
        transaction.setTransactionAt(Instant.now());
        transaction.setName("Water");
        transaction.setDescription("Water Bill");
        transaction.setPrice(BigDecimal.TEN);
        transaction.getTags().add(make(transaction, "housing"));
        transaction.getTags().add(make(transaction, "bill"));

        var transactionResponse = transactionMapper.parse(transaction);
        Assertions.assertEquals(transactionResponse.id(), transaction.getId());
        Assertions.assertEquals(transactionResponse.account(), transaction.getAccount());
        Assertions.assertEquals(transactionResponse.type(), transaction.getType());
        Assertions.assertEquals(transactionResponse.transactionAt(), transaction.getTransactionAt());
        Assertions.assertEquals(transactionResponse.name(), transaction.getName());
        Assertions.assertEquals(transactionResponse.description(), transaction.getDescription());
        Assertions.assertEquals(transactionResponse.price(), transaction.getPrice());
        Assertions.assertEquals(transactionResponse.tags().size(), transaction.getTags().size());

        for (var tag : transactionResponse.tags()) {
            var tagModel = transaction.getTags().stream().filter(t -> tag.id().equals(t.getId())).findFirst();
            Assertions.assertTrue(tagModel.isPresent());
            Assertions.assertEquals(tag.account(), tagModel.get().getAccount());
            Assertions.assertEquals(tag.name(), tagModel.get().getName());
        }
    }

    private Tag make(Transaction transaction, String tagName) {
        var tag = new Tag(transaction.getAccount(), tagName);
        tag.setId(UUID.randomUUID());
        tag.getTransactions().add(transaction);
        return tag;
    }
}
