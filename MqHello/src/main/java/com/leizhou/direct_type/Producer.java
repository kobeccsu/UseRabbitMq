package com.leizhou.direct_type;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

public class Producer {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();
        System.out.println("input message to send...");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){

            String input = scanner.next();

            channel.basicPublish(EXCHANGE_NAME, "error", null, input.getBytes("UTF-8"));
            System.out.println("Sent!");
        }
    }
}
