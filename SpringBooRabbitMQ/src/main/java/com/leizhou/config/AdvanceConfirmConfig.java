package com.leizhou.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    in some cases, when the rabbit mq is down, need to handle this
 */
@Configuration
public class AdvanceConfirmConfig {

    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";
    public static final String CONFIRM_ROUTING_KEY = "confirm_routingkey";
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    public static final String WARNING_QUEUE_NAME = "warning_queue";


    @Bean(CONFIRM_EXCHANGE_NAME)
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).alternate(BACKUP_EXCHANGE_NAME).build();
    }

    @Bean(CONFIRM_QUEUE_NAME)
    public Queue confirmQueue(){
        return new Queue(CONFIRM_QUEUE_NAME);
    }

    @Bean
    public Binding confirmBinding(@Qualifier(CONFIRM_QUEUE_NAME) Queue queue, @Qualifier(CONFIRM_EXCHANGE_NAME) DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean(BACKUP_EXCHANGE_NAME)
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean(BACKUP_QUEUE_NAME)
    public Queue backupQueue(){
        return new Queue(BACKUP_QUEUE_NAME);
    }

    @Bean(WARNING_QUEUE_NAME)
    public  Queue warningQueue(){
        return new Queue(WARNING_QUEUE_NAME);
    }

    @Bean
    public Binding backUpExchangeToBackupQueue(@Qualifier(BACKUP_QUEUE_NAME) Queue queue, @Qualifier(BACKUP_EXCHANGE_NAME) FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding backUpExchangeToWarningQueue(@Qualifier(WARNING_QUEUE_NAME) Queue queue, @Qualifier(BACKUP_EXCHANGE_NAME) FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
}
