package com.leizhou.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String message) {
        log.info("Current time {}, send msg: {}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("normal_exchange", "XA", " message from 10s XA :" + message);
        rabbitTemplate.convertAndSend("normal_exchange", "XB", " message from 40s XB :" + message);
    }

    @GetMapping("/sendMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable("message") String message, @PathVariable int ttlTime) {
        log.info("Current time {}, send msg: {}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("normal_exchange", "XC", " message from " + ttlTime + "s XA :" + message, msg -> {
            msg.getMessageProperties().setExpiration(String.valueOf(ttlTime));
            return msg;
        });

    }

    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendDelayMsg(@PathVariable("message") String message, @PathVariable int delayTime) {
        log.info("Current time {}, send msg: {}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("delayed.exchange", "delayed.routingkey",
                " message from " + delayTime + "s to delay queue :" + message,
                msg -> {
                    msg.getMessageProperties().setDelay(delayTime * 1000);
                    return msg;
                });

    }
}
