package com.es.account.repository;

import com.es.account.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class AccountAuditListener {

    @PostPersist
    private void postPersist(Account entity) {
        log.info("Account [ " + entity.getId() + " ]被更新");
    }

    @PostRemove
    private void PostRemove(Account entity) {
        log.info("Account [ " + entity.getId() + " ]被删除");
    }

    @PostUpdate
    private void PostUpdate(Account entity) {
        log.info("Account [ " + entity.getId() + " ]被更新");
    }
}
