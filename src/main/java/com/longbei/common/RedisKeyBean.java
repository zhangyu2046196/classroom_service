package com.longbei.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangy
 * @version 1.0
 * @description  redis的key实体
 * @date 2020/3/18 10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisKeyBean implements Serializable {
    private static final long serialVersionUID = 3260332699104361918L;

    /**
     * key前缀
     */
    private String prefixKey;

    /**
     * key对应的数据库字段
     */
    private String keyColumn;
}
