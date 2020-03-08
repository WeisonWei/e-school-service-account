package com.es.account.service;

import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.account.modle.AccountVo;
import org.springframework.data.domain.Page;

import java.util.List;


public interface AccountService {

    Account getAccount(long userId, Account.AccountType accountType);

    Account getLastAccount(long userId, Account.AccountType accountType);

    Account getLastCashOrPointsAccount(long userId, Account.AccountType accountType);

    Page<Account> getAccounts(Account.AccountType accountType, int page);

    AccountVo getAccountInfo(long userId, Account.AccountType accountType);

    AccountVo getPeriodInComeAccountInfo(long userId, Account.AccountType accountType);

    Page<Account> getAccountStatementPage(long userId, Account.AccountType accountType, Integer type, int page);

    List<Account> getAccountStatements(long userId, Account.AccountType accountType, Integer type);

    void initAccounts(Long userId);

    Account initAccount(long userId, Account.AccountType accountType);

    void initAccountIfNotExist(long userId, Account.AccountType accountType);

    Account updateAccountByTransaction(Transaction transaction);

    Account updateAccountByExchangeTransaction(Transaction transaction);

    Float getRedEnvelope(Long inviteSourceId);

}
