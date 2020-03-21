package com.longbei.service;

import com.longbei.config.RedisCacheManager;
import com.longbei.service.api.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangy
 * @version 1.0
 * @description
 * @date 2020/3/17 13:48
 */
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @Autowired
    private RedisCacheManager redisCacheManager;


    @Override
    public void kafkaConsumer() {

    }
}
