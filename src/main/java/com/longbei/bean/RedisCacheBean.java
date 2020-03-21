package com.longbei.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangy
 * @version 1.0
 * @description  LBRedisCache实体
 * @date 2020/3/11 12:05
 */
@Data
public class RedisCacheBean implements Serializable {
    private static final long serialVersionUID = -149816436251817537L;

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private String redisValue;

    /**
     * 前缀
     */
    private String prefixKey;

    /**
     * 完整key
     */
    private String fullKey;

}
