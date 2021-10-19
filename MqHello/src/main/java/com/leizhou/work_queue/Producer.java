package com.leizhou.work_queue;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static com.leizhou.utils.MqUtil.getChannel;

public class Producer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println("sent " + message);
        }
    }
}
