package com.es.account.repository.custom;

import com.es.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomRepository extends JpaSpecificationExecutor<Account>, JpaRepository<Account, Long> {
}
