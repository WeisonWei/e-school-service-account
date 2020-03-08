package com.es.account.service;

import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;


public interface TransactionService {

    Transaction updateTransaction(Transaction transaction);

    Transaction createCashTransaction(Transaction transaction);

    Transaction createExchangeTransaction(Transaction transaction);

    List<Transaction> getTransactions(long userId, Account.AccountType accountType);

    List<Transaction> getTransactions(long userId);

    Page<Transaction> getWithdrawalTransactions(int page);

    Page<Transaction> getPagedTransactions(long userId, Account.AccountType accountType,
                                           Transaction.TransType transType,
                                           Transaction.STATUS status, int page);

}
