package com.es.account.constant;

import com.es.account.entity.Account;
import com.es.account.repository.AccountRepository;
import com.es.base.util.DataUtils;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback
public class AmountCollectionTest {

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void initData() throws Exception {
        Account cashAccountIn = DataUtils.mockObj(Account.class);
        cashAccountIn.setAccountType(Account.AccountType.CASH);
        cashAccountIn.setUserId(1l);
        cashAccountIn.setId(1l);
        Account pointsAccountIn = DataUtils.mockObj(Account.class);
        pointsAccountIn.setAccountType(Account.AccountType.POINTS);
        pointsAccountIn.setUserId(1l);
        pointsAccountIn.setId(2l);
       

        Account cashAccountOut = DataUtils.mockObj(Account.class);
        cashAccountOut.setAccountType(Account.AccountType.CASH);
        cashAccountOut.setUserId(1l);
        cashAccountOut.setId(4l);
        Account pointsAccountOut = DataUtils.mockObj(Account.class);
        pointsAccountOut.setAccountType(Account.AccountType.POINTS);
        pointsAccountOut.setUserId(1l);
        pointsAccountOut.setId(5l);
   

        accountRepository.save(cashAccountIn);
        accountRepository.save(pointsAccountIn);

        accountRepository.save(cashAccountOut);
        accountRepository.save(pointsAccountOut);
    }

    @Test
    public void getCashInAmountPage() {
        Page<Account> amountPage = AmountCollection.getAmountPage(1, Account.AccountType.CASH, 0, 0, accountRepository);
        List<Account> accounts = amountPage.getContent();
        TestCase.assertTrue(accounts.size() == 1 && accounts.get(0).getUserId() == 1l);
    }

    @Test
    public void getCashOutAmountPage() {
        Page<Account> amountPage = AmountCollection.getAmountPage(1, Account.AccountType.CASH, 1, 0, accountRepository);
        List<Account> accounts = amountPage.getContent();
        TestCase.assertTrue(accounts.size() == 1 && accounts.get(0).getUserId() == 1l);
    }

    @Test
    public void getCashInAmountList() {
        List<Account> amountList = AmountCollection.getAmountList(1, Account.AccountType.CASH, 0, accountRepository);
        TestCase.assertTrue(amountList.size() == 1 && amountList.get(0).getUserId() == 1l);
    }

    @Test
    public void getCashOutAmountList() {
        List<Account> amountList = AmountCollection.getAmountList(1, Account.AccountType.CASH, 1, accountRepository);
        TestCase.assertTrue(amountList.size() == 1 && amountList.get(0).getUserId() == 1l);
    }

    @Test
    public void getPointsInAmountPage() {
        Page<Account> amountPage = AmountCollection.getAmountPage(1, Account.AccountType.POINTS, 0, 0, accountRepository);
        List<Account> accounts = amountPage.getContent();
        TestCase.assertTrue(accounts.size() == 1 && accounts.get(0).getUserId() == 1l);
    }

    @Test
    public void getPointsOutAmountPage() {
        Page<Account> amountPage = AmountCollection.getAmountPage(1, Account.AccountType.POINTS, 1, 0, accountRepository);
        List<Account> accounts = amountPage.getContent();
        TestCase.assertTrue(accounts.size() == 1 && accounts.get(0).getUserId() == 1l);
    }

    @Test
    public void getPointsInAmountList() {
        List<Account> amountList = AmountCollection.getAmountList(1, Account.AccountType.POINTS, 0, accountRepository);
        TestCase.assertTrue(amountList.size() == 1 && amountList.get(0).getUserId() == 1l);
    }

    @Test
    public void getPointsOutAmountList() {
        List<Account> amountList = AmountCollection.getAmountList(1, Account.AccountType.POINTS, 1, accountRepository);
        TestCase.assertTrue(amountList.size() == 1 && amountList.get(0).getUserId() == 1l);
    }

}
