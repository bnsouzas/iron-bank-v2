package com.buddycash.ironbank.domain.transactions.repositories;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionMongoRepository extends MongoRepository<TransactionResponse, UUID> {
}
