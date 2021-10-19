package com.leizhou.dead_msg;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import sun.swing.AccumulativeRunnable;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer1 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = MqUtil.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // these settings determine the condition to dead exchange
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "lisi");
//        arguments.put("x-max-length", 6);

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        System.out.println("waiting message");

        channel.basicConsume(NORMAL_QUEUE, false, ((consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");

            if(msg.contains("5")){
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                System.out.println("reject message " + msg);
            }else{
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                System.out.println("acknowledge message " + msg);
            }

            System.out.println("Consumer1 get message -->> " + msg);
        }), (consumerTag -> {}));
    }
}
