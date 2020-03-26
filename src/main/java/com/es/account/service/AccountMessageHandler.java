package com.es.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountMessageHandler {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;


    @StreamListener(value = Sink.INPUT, condition = "headers['type'] == 'Message'")
    public void processMessage(String message) {

    }

}
