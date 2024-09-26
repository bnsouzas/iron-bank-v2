package com.buddycash.ironbank.domain.transactions.repositories;

import com.buddycash.ironbank.domain.transactions.models.Tag;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, UUID>, JpaSpecificationExecutor<Tag> {
  Optional<Tag> findByAccountAndName(UUID accountId, String name);
}
