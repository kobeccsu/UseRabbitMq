package com.leizhou.exchange;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiverLogs1 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String queue = channel.queueDeclare().getQueue();

        channel.queueBind(queue, EXCHANGE_NAME, "");

        System.out.println("Receiver 1 ,Waiting message...");

        channel.basicConsume(queue, (consumerTag, message) -> {
            System.out.println("Receiver 1 ,Get message " + new String(message.getBody()));
        } , consumerTag -> {} );
    }
}
