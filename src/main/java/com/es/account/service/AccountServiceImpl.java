package com.es.account.service;

import com.es.account.constant.AmountCollection;
import com.es.account.constant.RedEnvelope;
import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.account.exception.AccountException;
import com.es.account.repository.AccountRepository;
import com.es.api.model.AccountVo;
import com.es.base.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.es.account.entity.Account.TransType.RED_ENVELOPE;
import static com.es.base.util.DateUtils.getMonthFirstDateMill;
import static com.es.base.util.DateUtils.getMonthLastDateMill;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMessageHandler accountMessageHandler;

    @Autowired
    private AccountService accountService;

    @Override
    public Account getAccount(long userId, Account.AccountType accountType) {
        //不存在则插入Init数据
        return accountService.getLastCashOrPointsAccount(userId, accountType);
    }

    @Override
    public Page<Account> getAccounts(Account.AccountType accountType, int page) {
        return accountRepository.findAllByAccountType(accountType, PageRequest.of(page, 15));
    }

    @Override
    public AccountVo getAccountInfo(long userId, Account.AccountType accountType) {

        float balance = accountService.getLastCashOrPointsAccount(userId, accountType).getBalance();

        List<Account> inComeAmountList = AmountCollection.getAmountList(userId, accountType, 0, accountRepository);
        float inComeTotalAmount = sumAmount(inComeAmountList);

        List<Account> expendAmountList = AmountCollection.getAmountList(userId, accountType, 1, accountRepository);
        float expendTotalAmount = sumAmount(expendAmountList);

        return AccountVo.builder()
                .accountType(AccountVo.AccountType.valueOf(accountType.toString()))
                .balance(balance)
                .userId(userId)
                .build();
    }

    @Override
    public AccountVo getPeriodInComeAccountInfo(long userId, Account.AccountType accountType) {

        List<Account.TransType> transTypes = new ArrayList<>();

        if (accountType == Account.AccountType.CASH) {
            transTypes = Stream.of(RED_ENVELOPE, Account.TransType.INIT).collect(toList());
        }
        List<Account> accounts = accountRepository.findAllByUserIdAndAccountTypeAndUpdateTimeBetween(userId, accountType,
                getMonthFirstDateMill(), getMonthLastDateMill());
        float inComeAmountTotal = sumAmount(accounts);
        return AccountVo.builder()
                .accountType(AccountVo.AccountType.valueOf(accountType.toString()))
                .userId(userId)
                .build();
    }

    @Override
    public Page<Account> getAccountStatementPage(long userId, Account.AccountType accountType, Integer type, int page) {
        return AmountCollection.getAmountPage(userId, accountType, type, page, accountRepository);
    }

    @Override
    public List<Account> getAccountStatements(long userId, Account.AccountType accountType, Integer type) {
        return AmountCollection.getAmountList(userId, accountType, type, accountRepository)
                .parallelStream()
                .collect(toList());
    }

    @Override
    public Account getLastAccount(long userId, Account.AccountType accountType) {
        return accountService.getLastCashOrPointsAccount(userId, accountType);
    }


    public Account getLastCashOrPointsAccount(long userId, Account.AccountType accountType) {
        return accountRepository.findFirst1ByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, accountType)
                .orElseGet(() -> accountService.initAccount(userId, accountType));
    }

    @Override
    public void initAccounts(Long userId) {
        List<Account> accounts = buildInitAccounts(userId);
        List<Account> noExistAccounts = excludeIfExists(accounts);
        accountRepository.saveAll(noExistAccounts);
        log.info("initAccounts end");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initAccountIfNotExist(long userId, Account.AccountType accountType) {
        Boolean isExists = accountRepository.existsByUserIdAndAccountType(userId, accountType);
        if (Boolean.FALSE.equals(isExists)) {
            accountRepository.save(buildAccount(userId, accountType, "initAccountWhenNotExist"));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Account initAccount(long userId, Account.AccountType accountType) {
        return accountRepository.save(buildAccount(userId, accountType, "initAccountWhenNotExist"));
    }

    @Override
    public Float getRedEnvelope(Long inviteSourceId) {
        return Optional.ofNullable(inviteSourceId)
                .map(this::calculateRedEnvelopeAmount)
                .orElseThrow(() -> new AccountException("userId为空!"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Account updateAccountByTransaction(Transaction transaction) {
        Float withdrawalLimit = 1f;

        if (transaction.getAmount() < withdrawalLimit) {
            throw new AccountException("交易额度需大于" + withdrawalLimit + "元!");
        }

        Long userId = transaction.getUserId();
        Account.AccountType accountType = transaction.getAccountType();
        Account lastAccount = getLastAccount(userId, accountType);

        if (lastAccount.isBalanceEnough(transaction.getAmount())) {
            return accountRepository.save(lastAccount);
        } else {
            throw new AccountException("账户余额不足!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Account updateAccountByExchangeTransaction(Transaction transaction) {
        Long userId = transaction.getUserId();
        Account.AccountType accountType = transaction.getAccountType();
        Account lastAccount = accountService.getLastAccount(userId, accountType);
        if (lastAccount.isBalanceEnough(transaction.getAmount())) {
            return accountRepository.save(lastAccount);
        } else {
            throw new AccountException("积分余额不足!");
        }
    }

    private float sumAmount(List<Account> accounts) {
        return accounts.stream()
                .map(account -> DataUtils.getBigDecimal(account.getBalance()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .floatValue();
    }

    private List<Account> excludeIfExists(List<Account> accounts) {
        return accounts.parallelStream()
                .filter(account -> !accountRepository.existsByUserIdAndAccountType(account.getUserId(), account.getAccountType()))
                .collect(toList());
    }

    private List<Account> buildInitAccounts(Long userId) {
        return Stream.of(
                buildAccount(userId, Account.AccountType.CASH, "用户注册"),
                buildAccount(userId, Account.AccountType.POINTS, "用户注册")
        ).collect(toList());
    }

    private Account buildAccount(Long userId, Account.AccountType accountType, String desc) {
        return new Account()
                .setUserId(userId)
                .setAccountType(accountType)
                .setRemark(desc)
                .setBalance(0f);
    }

    /**
     * 生成随机红包金额
     *
     * @return
     */
    private float calculateRedEnvelopeAmount(Long userId) {
        return RedEnvelope.getRedEnvelopeAmount(1l);
    }

}
