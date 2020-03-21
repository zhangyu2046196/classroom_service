package com.longbei.annotation;

import com.longbei.common.RedisType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhangyu
 * @description redis缓存注解
 * @date 2020/3/11 10:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
public @interface LBRedisCache {

    /**
     * key前缀
     *
     * @return
     */
    String prefixKey() default "";

    /**
     * key
     *
     * @return
     */
    String key() default "";

    /**
     * redisValue
     *
     * @return
     */
    String redisValue() default "";

    /**
     * 类型 type 1往redis写入   2从redis删除
     *
     * @return
     */
    RedisType type();

}
