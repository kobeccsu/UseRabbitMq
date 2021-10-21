package com.leizhou.controller;

import com.leizhou.config.AdvanceConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@Slf4j
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{content}")
    public void sendMessage(@PathVariable String content){

        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(AdvanceConfirmConfig.CONFIRM_EXCHANGE_NAME, AdvanceConfirmConfig.CONFIRM_ROUTING_KEY,
                content, correlationData);
        log.info("Producer sent " + content + " routingkey : k1");

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(AdvanceConfirmConfig.CONFIRM_EXCHANGE_NAME, AdvanceConfirmConfig.CONFIRM_ROUTING_KEY + "cant",
                content, correlationData2);
        log.info("Producer sent " + content+ " routingkey : k12");

    }
}
