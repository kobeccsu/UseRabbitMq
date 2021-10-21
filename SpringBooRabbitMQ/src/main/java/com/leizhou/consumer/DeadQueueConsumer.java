package com.leizhou.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DeadQueueConsumer {

    @RabbitListener(queues = "dead_queue")
    public void receive(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("Receiver {} , got :{}", new Date().toString(), msg);
    }
}
