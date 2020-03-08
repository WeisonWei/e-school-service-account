package com.es.account.repository;

import com.es.account.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, CrudRepository<Account, Long> {

    Stream<Account> findAllByAccountType(long userId, Account.AccountType accountType);

    Stream<Account> findAllByUserIdAndAccountType(long userId, Account.AccountType accountType);

    Page<Account> findAllByAccountType(Account.AccountType accountType, Pageable pageable);

    List<Account> findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(long userId, Account.AccountType accountType);

    Page<Account> findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(long userId, Account.AccountType accountType, Pageable pageable);

    List<Account> findAllByUserIdAndAccountTypeAndUpdateTimeBetween(long userId, Account.AccountType accountType, Long begin, Long end);

    Optional<Account> findFirst1ByUserIdAndAccountTypeOrderByUpdateTimeDesc(long userId, Account.AccountType accountType);

    @Async
    CompletableFuture<Optional<Account>> findTop1ByUserIdAndAccountTypeOrderByUpdateTimeDesc(long userId, Account.AccountType accountType);

    @Query(value = " from Account as a where a.userId = ?1  and CONCAT(a.accountType,a.update_time) " +
            "in (select CONCAT(accountType,max(update_time)) from Account where accountType = ?2 group by accountType) ")
    List<Account> findLastAccountsByAccountType(long userId, Account.AccountType accountType);

    Boolean existsByUserIdAndAccountType(long userId, Account.AccountType accountType);

    Optional<Account> findByUserIdAndAccountTypeAndSource(long userId, Account.AccountType accountType, long source);

}
