package com.buddycash.ironbank.domain.transactions.repositories;

import com.buddycash.ironbank.domain.transactions.models.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository
    extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
  @Query("select t from Transaction t left join fetch t.tags where t.id = ?2 and t.account = ?1")
  Optional<Transaction> findByAccountAndId(UUID accountId, UUID id);

  @Query("select t from Transaction t left join fetch t.tags where t.account = ?1")
  List<Transaction> findAllByAccount(UUID accountId);
}
