package com.leizhou.consumer;

import com.leizhou.config.DelayQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DelayMessageConsumer {

    @RabbitListener(queues = DelayQueueConfig.DELAY_QUEUE_NAME)
    public void receiverDelayMessage(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("Delayed Receiver {} , got :{}", new Date().toString(), msg);
    }
}
