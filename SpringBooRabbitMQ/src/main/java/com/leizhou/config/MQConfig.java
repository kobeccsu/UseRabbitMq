package com.leizhou.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE_A = "normal_queue_a";
    public static final String NORMAL_QUEUE_B = "normal_queue_b";

    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static final String NORMAL_QUEUE_C = "normal_queue_c";


    @Bean(NORMAL_EXCHANGE)
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    @Bean(DEAD_EXCHANGE)
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    @Bean(NORMAL_QUEUE_A)
    public Queue normalQueueA() {
        return QueueBuilder.durable(NORMAL_QUEUE_A)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .ttl(10000)
                .build();
    }

    @Bean(NORMAL_QUEUE_B)
    public Queue normalQueueB() {
        return QueueBuilder.durable(NORMAL_QUEUE_B)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .ttl(40000)
                .build();
    }

    @Bean(NORMAL_QUEUE_C)
    public Queue normalQueueC() {
        return QueueBuilder.durable(NORMAL_QUEUE_C)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }

    @Bean(DEAD_QUEUE)
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    @Bean
    public Binding qNormalAToNormalExchange(
            @Qualifier(NORMAL_QUEUE_A) Queue queue,
            @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("XA");
    }

    @Bean
    public Binding qNormalBToNormalExchange(
            @Qualifier(NORMAL_QUEUE_B) Queue queue,
            @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("XB");
    }

    @Bean
    public Binding qNormalCToNormalExchange(
            @Qualifier(NORMAL_QUEUE_C) Queue queue,
            @Qualifier(NORMAL_EXCHANGE) DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("XC");
    }

    @Bean
    public Binding deadToDeadExchange(
            @Qualifier(DEAD_QUEUE) Queue queue,
            @Qualifier(DEAD_EXCHANGE) DirectExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with("YD");


    }
}
