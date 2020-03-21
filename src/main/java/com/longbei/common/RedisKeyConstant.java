package com.longbei.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangy
 * @version 1.0
 * @description redis存储key的常量
 * @date 2020/3/11 12:23
 */
public class RedisKeyConstant {

    /**
     * 教室的key
     */
    public final static String CLASSROOM_PREFIX_KEY = "com.longbei.classroom.redis_";

    /**
     * 测试学生表
     */
    public final static String STUDENT_PREFIX_KEY = "com.longbei.student.redis_";

    /**
     * 表对应的key前缀和使用的数据库字段
     */
    public static Map<String, RedisKeyBean> keyMap = new HashMap<>();

    static {
        keyMap.put("student", new RedisKeyBean(STUDENT_PREFIX_KEY, "id"));
    }

}
