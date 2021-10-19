package com.leizhou.topic;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import sun.applet.Main;

public class Consumer1 {

    public static final String exchange_name = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();

        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.TOPIC);
        String queueName = "Q1";
        channel.queueDeclare(queueName, false,false,false,null);
        // binding routing key use match, then can match different queue,
        channel.queueBind(queueName, exchange_name, "*.orange.*");

        System.out.println("waiting message ...");

        channel.basicConsume(queueName, true, ((consumerTag, message) -> {
            System.out.println("Consumer 1 got message : " + new String(message.getBody()));

        }), consumerTag -> {});

    }
}
