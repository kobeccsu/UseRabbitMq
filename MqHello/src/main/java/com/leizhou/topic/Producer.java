package com.leizhou.topic;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

public class Producer {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = MqUtil.getChannel();

        Map<String, String> routingKeys = new HashMap<>();
        routingKeys.put("quick.orange.rabbit", "被队列Q1Q2接收到");
        routingKeys.put("lazy.orange.elephant", "被队列Q1Q2接收到");
        routingKeys.put("quick.orange.fox", "被队列Q1接收到");
        routingKeys.put("lazy.brown.fox", "被队列Q2接收到");

        for (Map.Entry<String, String> stringStringEntry : routingKeys.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();

            channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes("UTF-8"));

        }
    }
}
