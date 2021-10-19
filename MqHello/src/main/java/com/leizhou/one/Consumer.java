package com.leizhou.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.leizhou.utils.MqUtil.getChannel;

public class Consumer {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = getChannel();

        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("get the message " + new String(message.getBody()));
            System.out.println("success");
        }, (consumerTag) -> {
            System.out.println("cancel");
        });
    }


}
