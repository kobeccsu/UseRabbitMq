package com.leizhou.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.leizhou.utils.MqUtil.getChannel;

public class Producer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = getChannel();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10);
        // both queue and message need set priority
        channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);

        for (int i = 1; i <= 10; i++) {
            String msg =  "sending the " + i + " msg";
            if(i == 5){
                AMQP.BasicProperties build = new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("", QUEUE_NAME, build, msg.getBytes());
            }else{
                channel.basicPublish("", QUEUE_NAME,null, msg.getBytes());
            }
        }

        System.out.println("end");

    }
}
