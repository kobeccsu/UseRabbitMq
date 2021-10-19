package com.leizhou.publich_confirm;

import com.leizhou.utils.MqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.CharsetEncoder;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
//        publishSingleAck();  // 1327 ms
//        publishBatchAck();  // 111 ms
        publishAckAsync();
    }

    public static void publishSingleAck() throws IOException, TimeoutException, InterruptedException {
        Channel channel = MqUtil.getChannel();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            channel.basicPublish("", queueName, null, String.valueOf(i).getBytes());
            boolean b = channel.waitForConfirms();
//            if (b){
//                System.out.println("sent ok!");
//            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("time elapse " + (endTime - beginTime) + " ms");
    }

    public static void publishBatchAck() throws IOException, TimeoutException, InterruptedException {
        Channel channel = MqUtil.getChannel();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            channel.basicPublish("", queueName, null, String.valueOf(i).getBytes());
            if (i % 100 == 0) {
                channel.waitForConfirms();
            }
//            if (b){
//                System.out.println("sent ok!");
//            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("time elapse " + (endTime - beginTime) + " ms");
    }

    public static void publishAckAsync() throws Exception {
        Channel channel = MqUtil.getChannel();

        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);

        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        channel.addConfirmListener(
                (messageTag, isMulti) -> {
                    System.out.println("Is Multiple ?" + isMulti);
                    if(isMulti){
                        ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(messageTag);
                        confirmed.clear();
                    } else {
                        outstandingConfirms.remove(messageTag);
                    }

                    System.out.println("sent successfully " + messageTag);
                }, (messageTag, isMulti) -> {
                    String notAckMessage = outstandingConfirms.get(messageTag);

                    System.out.println("sent fail " + messageTag + " message is " + notAckMessage);
                });

        long beginTime = System.currentTimeMillis();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "Msg " + i;
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
            channel.basicPublish("", queueName, null, message.getBytes());

        }

        long endTime = System.currentTimeMillis();

        System.out.println("time elapse " + (endTime - beginTime) + " ms");
    }
}
