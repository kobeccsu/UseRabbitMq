package com.leizhou.config;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ConfirmSendCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String s = correlationData != null ? correlationData.getId() : "";

        if (ack) {
            log.info("Sent message : " + s + " successfully!");
        } else {
            log.info("Fail to send message, reason ==>" + cause);
        }
    }

    /*
     won't execute once the alternative exchange has been set.
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("Returned message {}, exchange : {}, routing key : {}, return reason : {}",
                returned.getMessage(), returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
    }
}
