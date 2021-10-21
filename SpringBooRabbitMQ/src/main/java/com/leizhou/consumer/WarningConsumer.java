package com.leizhou.consumer;

import com.leizhou.config.AdvanceConfirmConfig;
import com.leizhou.config.ConfirmSendCallback;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WarningConsumer {

    @RabbitListener(queues = AdvanceConfirmConfig.WARNING_QUEUE_NAME)
    public void consume(Message message, Channel channel){
        log.info("Warning consumer get msg ==> " + new String(message.getBody()));
    }
}
