package com.es.account.repository.custom;

import com.es.account.entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional(readOnly = true)
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CustomRepository customRepository;

    @Override
    public List<Account> getAddress(Account.AccountType accountType) {
        Query query = entityManager.createNativeQuery("SELECT u.* FROM Account as u WHERE u.city = ", Account.class);
        query.setParameter(1, accountType + "%");
        return query.getResultList();
    }

    public List<Account> findBetweenAge(long start, long end) {
        Specification between = SpecificationFactory.isBetween("balance", start, end);
        List<Account> all = customRepository.findAll(between);
        return all;
    }

    public Page<Account> findByCondition(Account account, Pageable pageable) {
        return customRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNoneBlank(account.getRemark())) {
                //liked的查询条件
                predicates.add(cb.like(root.get("remark"), "%" + account.getRemark() + "%"));
            }
            if (Objects.nonNull(account.getBalance())) {
                //balance
                predicates.add(cb.greaterThan(root.get("balance"), account.getBalance()));
            }
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        }, pageable);
    }
}
