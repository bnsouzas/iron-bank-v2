package com.buddycash.ironbank.domain.transactions.repositories;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import com.buddycash.ironbank.domain.transactions.models.Transaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface TransactionSearchRepository extends ElasticsearchRepository<TransactionResponse, String> {
}
