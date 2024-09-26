package com.buddycash.ironbank.domain.accounts.repositories;

import com.buddycash.ironbank.domain.accounts.model.Account;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository
    extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {}
