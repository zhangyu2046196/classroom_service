package com.longbei.controller;

import com.longbei.config.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author zhangy
 * @version 1.0
 * @description
 * @date 2020/3/11 9:28
 */
@RestController
public class TestRedisController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        System.out.println("进入test方法 " + LocalDateTime.now());
        int value = (int) (Math.random() * 100);
        String v2 = "v" + value;
        redisTemplate.opsForValue().set("name", v2);
        String name = redisTemplate.opsForValue().get("name");
        return name;
    }

    @ResponseBody
    @RequestMapping("/test1/{param}")
    public String test1(@PathVariable("param") String param) {
        System.out.println("进入test1方法 " + LocalDateTime.now());
        redisCacheManager.set(param, param);
        String name = (String) redisCacheManager.get(param);
        return name;
    }

}
