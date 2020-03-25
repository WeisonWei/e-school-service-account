package com.es.account.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * mq连接源定义
 * <p>
 * 其中类中的2个属性的值和properties里的配置需要一致
 **/
public interface MessageSource {

    // BOSS生产者
    String MESSAGE_OUTPUT = "message_output";
    // ECM消费者
    String MESSAGE_INPUT = "message_input";

    @Output(MESSAGE_OUTPUT)
    MessageChannel messageOutput();

    @Input(MESSAGE_INPUT)
    MessageChannel messageInput();

}