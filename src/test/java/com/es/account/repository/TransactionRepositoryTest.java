package com.es.account.repository;

import com.es.account.common.RepositoryTest;
import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.base.util.DataUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class TransactionRepositoryTest extends RepositoryTest<TransactionRepository, Transaction> {

    private static long thisUserId = DataUtils.mockLong();

    @Autowired
    protected TransactionRepository transactionRepository;

    @PostConstruct
    public void initRepository() {
        super.repository = transactionRepository;
        super.entity = new Transaction();
        entity.setUserId(thisUserId);
        entity.setStatus(Transaction.STATUS.COMPLETE);
        entity.setAccountType(Account.AccountType.CASH);
    }
}