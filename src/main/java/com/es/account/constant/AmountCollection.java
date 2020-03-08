package com.es.account.constant;

import com.es.account.config.TiFunction;
import com.es.account.entity.Account;
import com.es.account.exception.AccountException;
import com.es.account.exception.ExceptionCode;
import com.es.account.repository.AccountRepository;
import com.es.base.util.DataUtils;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;


public enum AmountCollection {

    CASH_IN((accountType, type) -> Account.AccountType.CASH == accountType && 0 == type,
            AmountCollection::getCashInComeAmountPage,
            AmountCollection::getCashInComeAmountList),
    CASH_OUT((accountType, type) -> Account.AccountType.CASH == accountType && 1 == type,
            AmountCollection::getCashOutGoAmountPage,
            AmountCollection::getCashOutGoAmountList),
    POINTS_IN((accountType, type) -> Account.AccountType.POINTS == accountType && 0 == type,
            AmountCollection::getPointsInComeAmountPage,
            AmountCollection::getPointsInComeAmountList),
    POINTS_OUT((accountType, type) -> Account.AccountType.POINTS == accountType && 1 == type,
            AmountCollection::getPointsOutGoAmountPage,
            AmountCollection::getPointsOutGoAmountList);

    @Getter
    private BiPredicate<Account.AccountType, Integer> biPredicate;

    @Getter
    private TiFunction<Long, Integer, AccountRepository, Page<Account>> pageFunction;

    @Getter
    private BiFunction<Long, AccountRepository, List<Account>> listFunction;


    AmountCollection(BiPredicate<Account.AccountType, Integer> biPredicate,
                     TiFunction<Long, Integer, AccountRepository, Page<Account>> pageFunction,
                     BiFunction<Long, AccountRepository, List<Account>> listFunction) {
        this.biPredicate = biPredicate;
        this.pageFunction = pageFunction;
        this.listFunction = listFunction;
    }

    public static Page<Account> getAmountPage(long userId, Account.AccountType accountType, Integer type, int page, AccountRepository repository) {
        return Arrays.stream(AmountCollection.values())
                .filter(amountCollection -> amountCollection.getBiPredicate().test(accountType, type))
                .findAny()
                .map(amountCollection -> amountCollection.getPageFunction().apply(userId, page, repository))
                .orElseThrow(() -> new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND.getCode(), ExceptionCode.ACCOUNT_NOT_FOUND.getDesc()));
    }

    public static List<Account> getAmountList(long userId, Account.AccountType accountType, Integer type, AccountRepository repository) {
        return Arrays.stream(AmountCollection.values())
                .filter(amountCollection -> amountCollection.getBiPredicate().test(accountType, type))
                .findAny()
                .map(amountCollection -> amountCollection.getListFunction().apply(userId, repository))
                .orElseThrow(() -> new AccountException(ExceptionCode.ACCOUNT_NOT_FOUND.getCode(), ExceptionCode.ACCOUNT_NOT_FOUND.getDesc()));
    }

    private static Page<Account> getCashInComeAmountPage(Long userId, Integer page, AccountRepository repository) {
        return getPageAmountList(userId, page, Account.AccountType.CASH, repository);
    }

    private static Page<Account> getCashOutGoAmountPage(Long userId, Integer page, AccountRepository repository) {
        return getPageAmount(userId, page, Account.AccountType.CASH, repository);
    }

    private static List<Account> getCashInComeAmountList(Long userId, AccountRepository repository) {
        return getAmountListList(userId, Account.AccountType.CASH, repository);
    }

    private static List<Account> getCashOutGoAmountList(Long userId, AccountRepository repository) {
        return getAmountList(userId, Account.AccountType.CASH, repository);
    }

    private static Page<Account> getPointsInComeAmountPage(Long userId, Integer page, AccountRepository repository) {
        return getPageAmountList(userId, page, Account.AccountType.POINTS, repository);
    }

    private static Page<Account> getPointsOutGoAmountPage(Long userId, Integer page, AccountRepository repository) {
        return getPageAmount(userId, page, Account.AccountType.POINTS, repository);
    }

    private static List<Account> getPointsInComeAmountList(Long userId, AccountRepository repository) {
        return getAmountListList(userId, Account.AccountType.POINTS, repository);
    }

    private static List<Account> getPointsOutGoAmountList(Long userId, AccountRepository repository) {
        return getAmountList(userId, Account.AccountType.POINTS, repository);
    }

    private static Page<Account> getPageAmount(Long userId, Integer page, Account.AccountType accountType, AccountRepository repository) {
        List<Account> list = repository.findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, accountType);
        Page<Account> resultPage = DataUtils.listToPage(list, page);
        return resultPage;
    }

    private static Page<Account> getPageAmountList(Long userId, Integer page, Account.AccountType accountType, AccountRepository repository) {
        List<Account> list = repository.findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, accountType);
        Page<Account> resultPage = DataUtils.listToPage(list, page);
        return resultPage;
    }

    private static List<Account> getAmountList(Long userId, Account.AccountType accountType, AccountRepository repository) {
        List<Account> list = repository.findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, accountType);
        return list;
    }

    private static List<Account> getAmountListList(Long userId, Account.AccountType accountType, AccountRepository repository) {
        List<Account> list = repository.findAllByUserIdAndAccountTypeOrderByUpdateTimeDesc(userId, accountType);
        return list;
    }
}
