package com.leizhou.consumer;

import com.leizhou.config.AdvanceConfirmConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = AdvanceConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receive(Message message, Channel channel){
        log.info("Consumer got " + new String(message.getBody()));
    }
}
