package com.leizhou.dead_msg;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Producer {

    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = MqUtil.getChannel();

//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 1 ; i<= 10;i++ ) {
            String msg = "message " + i;
//            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, msg.getBytes("UTF-8"));
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, msg.getBytes("UTF-8"));

        }
    }
}
