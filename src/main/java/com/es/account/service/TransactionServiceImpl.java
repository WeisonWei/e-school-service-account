package com.es.account.service;

import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.account.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    //@Autowired
    //private AccountConfig accountConfig;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction createCashTransaction(Transaction transaction) {

        transaction.setStatus(Transaction.STATUS.COMPLETE);
        transaction = transactionRepository.save(transaction);
        try {
            Account account = accountService.updateAccountByTransaction(transaction);
        } catch (Exception e) {
            transaction.setStatus(Transaction.STATUS.FAILED);
            transaction.setTransReturn(e.getMessage());
        }
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Transaction createExchangeTransaction(Transaction transaction) {
        transaction.setStatus(Transaction.STATUS.COMPLETE);
        Transaction save = transactionRepository.save(transaction);
        try {
            Account account = accountService.updateAccountByExchangeTransaction(save);
            return save;
        } catch (Exception e) {
            save.setStatus(Transaction.STATUS.FAILED);
            save.setTransReturn(e.getMessage());
            return transactionRepository.save(save);
        }
    }

    @Override
    public List<Transaction> getTransactions(long userId, Account.AccountType accountType) {
        return transactionRepository.findAllByUserIdAndAccountType(userId, accountType);
    }

    @Override
    public List<Transaction> getTransactions(long userId) {
        return transactionRepository.findAllByuserId(userId);
    }

    @Override
    public Page<Transaction> getWithdrawalTransactions(int page) {
        return transactionRepository.findAllByAccountTypeAndTransTypeAndStatus(Account.AccountType.CASH,
                Transaction.TransType.WITHDRAWAL, Transaction.STATUS.COMMIT, PageRequest.of(page, 15));
    }

    @Override
    public Page<Transaction> getPagedTransactions(long userId, Account.AccountType accountType,
                                                  Transaction.TransType transType,
                                                  Transaction.STATUS status, int page) {
        Pageable pageable = PageRequest.of(page, 15);
        return transactionRepository.findAllByUserIdAndAccountTypeAndTransTypeAndStatus(userId,
                accountType, transType, status, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public Transaction updateTransaction(Transaction transaction) {
        transaction.setStatus(Transaction.STATUS.COMPLETE);
        return transactionRepository.save(transaction);
    }

    private CompletableFuture<Void> auditAndCompleteWithdrawalFuture(Transaction transaction) {
        return CompletableFuture.supplyAsync(() -> accountService.updateAccountByTransaction(transaction))
                .handle((account, throwable) -> {
                    if (throwable == null) {
                        transaction.setStatus(Transaction.STATUS.COMPLETE);
                        return transaction;
                    } else {
                        transaction.setStatus(Transaction.STATUS.FAILED);
                        return transaction.setTransReturn(throwable.getMessage());
                    }
                })
                .thenAccept(transactionRepository::save);
    }
}