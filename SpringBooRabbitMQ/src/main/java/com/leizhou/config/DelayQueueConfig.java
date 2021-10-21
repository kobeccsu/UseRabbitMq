package com.leizhou.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayQueueConfig {

    public static final String DELAY_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAY_QUEUE_NAME = "delayed.queue";
    public static final String DELAY_ROUTING_KEY = "delayed.routingkey";

    @Bean
    public CustomExchange delayedExchange(){
        HashMap<String, Object> arguments = new HashMap<>();

        // below config determine it's a delayed exchange
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, arguments);
    }

    @Bean
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAY_QUEUE_NAME).build();
    }

    @Bean
    public Binding binding(@Qualifier("delayedQueue") Queue delayedQueue, @Qualifier("delayedExchange") CustomExchange delayedExchange){

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAY_ROUTING_KEY).noargs();
    }
}
