package com.leizhou.exchange;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.util.Scanner;

public class ProducerLog {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        System.out.println("input message to send...");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){

            String input = scanner.next();

            channel.basicPublish(EXCHANGE_NAME, "", null, input.getBytes("UTF-8"));
            System.out.println("Sent!");
        }
    }
}
