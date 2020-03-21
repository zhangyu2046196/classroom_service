package com.longbei.kafka;

/**
 * @author zhangy
 * @version 1.0
 * @description
 * @date 2020/3/16 13:53
 */
public class AbstractKafkaTest {

    public final static String zkServers="192.168.1.18:2181,192.168.1.19:2181,192.168.1.20:2181";

    public final static String kafkaServers="192.168.1.18:9092,192.168.1.19:9092,192.168.1.20:9092";

    public final static String topic="order.student";

    public final static int partition=0;

    public final static String groupId=topic+"_123";

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
