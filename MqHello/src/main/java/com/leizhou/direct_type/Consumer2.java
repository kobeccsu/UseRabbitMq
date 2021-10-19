package com.leizhou.direct_type;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class Consumer2 {

    public static final String EXCHANGE_NAME = "direct_logs";
    public static final String QUEUE_NAME = "disk";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        // send to queue by route key
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

        channel.basicConsume(QUEUE_NAME, true,
                ((consumerTag, message) -> {
                    System.out.println("Consumer 2 got message : " + new String(message.getBody()));
                }), (consumerTag -> {
                }));

    }
}
