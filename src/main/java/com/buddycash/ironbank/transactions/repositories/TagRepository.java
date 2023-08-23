package com.buddycash.ironbank.transactions.repositories;

import com.buddycash.ironbank.transactions.models.Tag;
import com.buddycash.ironbank.transactions.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID>, JpaSpecificationExecutor<Tag> {
    Optional<Tag> findByAccountAndName(UUID accountId, String name);
}