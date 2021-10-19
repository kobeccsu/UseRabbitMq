package com.leizhou.dead_msg;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class DeadConsumer2 {
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        System.out.println("waiting message");

        channel.basicConsume(DEAD_QUEUE, true, ((consumerTag, message) -> {
            System.out.println("Consumer2 get message -->> " + new String(message.getBody(), "UTF-8"));
        }), (consumerTag -> {}));
    }
}
