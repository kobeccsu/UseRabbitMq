package com.leizhou.work_queue;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.leizhou.utils.MqUtil.getChannel;

public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = getChannel();


        System.out.println("Consumer 1 getting....");


        channel.basicConsume(QUEUE_NAME, true, (consumerTag, message) -> {
            System.out.println("get the message " + new String(message.getBody()));
            System.out.println("success");
        }, (consumerTag) -> {
            System.out.println("cancel");
        });
    }
}
