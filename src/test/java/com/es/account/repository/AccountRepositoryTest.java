package com.es.account.repository;

import com.es.account.common.RepositoryTest;
import com.es.account.entity.Account;
import com.es.base.util.DataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class AccountRepositoryTest extends RepositoryTest<AccountRepository, Account> {

    private static long thisUserId = DataUtils.mockLong();

    @Autowired
    protected AccountRepository accountRepository;

    @PostConstruct
    public void initRepository() {
        super.repository = accountRepository;
        super.entity = new Account();

        entity.setUserId(thisUserId);
        entity.setBalance(1.1f);
        entity.setRemark("test1");
        entity.setAccountType(Account.AccountType.CASH);
    }

    @Test
    public void findFirst1ByuserIdAndAccountTypeOrderByUtimeDesc() {
        Long userId = DataUtils.mockLong();
        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(2.2f);
        account.setRemark("test2");
        account.setAccountType(Account.AccountType.CASH);
        Account save = repository.save(account);
        Optional<Account> account1 = repository.findFirst1ByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, Account.AccountType.CASH);
        assertTrue(account1.isPresent());
    }

    @Test
    @Transactional(readOnly = true)
    public void findAllByUserIdAndAccountType() {
        Stream<Account> allByUserIdAndAccountType = repository.findAllByUserIdAndAccountType(thisUserId, Account.AccountType.CASH);
        List<Account> lastAccounts = allByUserIdAndAccountType.collect(Collectors.toList());
        assertTrue(lastAccounts.size() >= 1);
    }

    @Test
    public void findLastAccountsByAccountType() {
        List<Account> lastAccounts = repository.findLastAccountsByAccountType(thisUserId, Account.AccountType.CASH);
        assertEquals(1, lastAccounts.size());
    }
}