package com.es.account.repository;

import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends org.springframework.data.repository.CrudRepository<Transaction, Long> {

    List<Transaction> findAllByUserIdAndAccountType(long userId, Account.AccountType accountType);

    List<Transaction> findAllByuserId(long userId);

    Page<Transaction> findAllByAccountTypeAndTransTypeAndStatus(Account.AccountType cash, Transaction.TransType transType,
                                                                Transaction.STATUS status, Pageable page);

    List<Transaction> getAllByAccountTypeAndTransTypeAndStatus(Account.AccountType accountType, Transaction.TransType transType,
                                                               Transaction.STATUS status);

    Page<Transaction> findAllByUserIdAndAccountTypeAndTransTypeAndStatus(long userId, Account.AccountType accountType, Transaction.TransType transType,
                                                                         Transaction.STATUS status, Pageable page);
}
