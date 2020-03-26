package com.es.account.repository.custom;


import com.es.account.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AccountCustomRepository {

    List<Account> getAddress(Account.AccountType accountType);

    public List<Account> findBetweenAge(long start, long end);

    Page<Account> findByCondition(Account account, Pageable pageable);

}
