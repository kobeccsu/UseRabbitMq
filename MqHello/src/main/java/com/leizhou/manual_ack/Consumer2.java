package com.leizhou.manual_ack;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();
        channel.basicQos(5);

        channel.basicConsume(QUEUE_NAME, false, (Consumer, message) -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Consumer2 received" + new String(message.getBody()));

            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        }, (messageTag) ->{
            System.out.println("Consumer2 canceled");
        });
    }
}
