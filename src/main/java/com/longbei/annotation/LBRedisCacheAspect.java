package com.longbei.annotation;

import com.alibaba.fastjson.JSONObject;
import com.longbei.bean.RedisCacheBean;
import com.longbei.common.RedisType;
import com.longbei.config.RedisCacheManager;
import com.longbei.util.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangy
 * @version 1.0
 * @description redis缓存切面
 * @date 2020/3/11 10:42
 */
@Slf4j
@Aspect
@Component
public class LBRedisCacheAspect {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Before("@annotation(LBRedisCache)")
    public void beforeMethod(JoinPoint joinPoint) {

        LBRedisCache lbRedisCache = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LBRedisCache.class);
        try {
            if (null != lbRedisCache && lbRedisCache.type() == RedisType.DELETE) {
                RedisCacheBean redisCacheBean = new RedisCacheBean();
                processAop(lbRedisCache, redisCacheBean, joinPoint);
                if (redisCacheManager.hasKey(redisCacheBean.getFullKey())) {
                    boolean isDel = redisCacheManager.del(redisCacheBean.getFullKey());
                    log.info("注解拿到的key:" + redisCacheBean.getFullKey() + " redis删除结果:" + isDel);
                }
            }
        } catch (Exception e) {
            log.error("自定义注解前置通知", e);
        }
    }

    @AfterReturning(value = "@annotation(LBRedisCache)", returning = "ret")
    public void afterReturning(JoinPoint joinPoint, Object ret) {

        LBRedisCache lbRedisCache = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LBRedisCache.class);
        try {
            if (null != lbRedisCache && lbRedisCache.type() == RedisType.SAVE && null != ret) {
                RedisCacheBean redisCacheBean = new RedisCacheBean();
                processAop(lbRedisCache, redisCacheBean, joinPoint);
                redisCacheBean.setRedisValue(JSONObject.toJSONString(ret));
                boolean isSave = redisCacheManager.set(redisCacheBean.getFullKey(), redisCacheBean.getRedisValue());
                log.info("注解拿到的key:" + redisCacheBean.getFullKey() + " 注解拿到的value:" + redisCacheBean.getRedisValue() + " redis保存结果:" + isSave);
            }
        } catch (Exception e) {
            log.error("自定义注解后置通知", e);
        }
    }


    /**
     * 处理注解
     *
     * @param lbRedisCache
     * @param redisCacheBean
     * @param joinPoint
     */
    private void processAop(LBRedisCache lbRedisCache, RedisCacheBean redisCacheBean, JoinPoint joinPoint) {
        try {
            //处理 自定义注解
            //处理key
            if (lbRedisCache != null && StringUtils.isNotBlank(lbRedisCache.key())) {
                Object fieldValue = SpelUtil.getSpelValue(lbRedisCache.key(), joinPoint);
                if (fieldValue != null) {
                    redisCacheBean.setKey(fieldValue.toString());
                }
            }
            //处理value
//            if (lbRedisCache != null && StringUtils.isNotBlank(lbRedisCache.redisValue())) {
//                Object fieldValue = SpelUtil.getSpelValue(lbRedisCache.redisValue(), joinPoint);
//                if (fieldValue != null) {
//                    redisCacheBean.setRedisValue(JSONObject.toJSONString(fieldValue));
//                }
//            }

            //处理 prefixKey
            if (lbRedisCache != null && StringUtils.isNotBlank(lbRedisCache.prefixKey())) {
                redisCacheBean.setPrefixKey(lbRedisCache.prefixKey());
            }

            redisCacheBean.setFullKey(redisCacheBean.getPrefixKey() + redisCacheBean.getKey());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    //    @Before("@annotation(LBRedisCache)")
//    public void beforeMethod(JoinPoint joinPoint) {
//
////        LBRedisCache lbRedisCache = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(LBRedisCache.class);
//
//        //获得当前访问的class
//        Class<?> className = joinPoint.getTarget().getClass();
//
//        //获得访问的方法名
//        String methodName = joinPoint.getSignature().getName();
//        //得到方法的参数的类型
//        Class[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
//        try {
//            // 得到访问的方法对象
//            Method method = className.getMethod(methodName, argClass);
//
//            // 判断是否存在@LBRedisCache注解
//            if (method.isAnnotationPresent(LBRedisCache.class)) {
//                LBRedisCache annotation = method.getAnnotation(LBRedisCache.class);
//
//                RedisCacheBean redisCacheBean = new RedisCacheBean();
//                processAop(annotation, redisCacheBean, joinPoint);
//                log.info("注解拿到的key:" + redisCacheBean.getFullKey() + " 注解拿到的value:" + redisCacheBean.getRedisValue());
//
//                RedisType redisType = annotation.type();
//                if (redisType.getType() == RedisType.SAVE.getType()) {//保存redis
//                    boolean isSave = redisCacheManager.set(redisCacheBean.getFullKey(), redisCacheBean.getRedisValue());
//                    log.info("注解拿到的key:" + redisCacheBean.getFullKey() + " 注解拿到的value:" + redisCacheBean.getRedisValue() + " redis保存结果:" + isSave);
//                } else if (redisType.getType() == RedisType.DELETE.getType()) {//删除redis
//                    if (redisCacheManager.hasKey(redisCacheBean.getFullKey())) {
//                        boolean isDel = redisCacheManager.del(redisCacheBean.getFullKey());
//                        log.info("注解拿到的key:" + redisCacheBean.getFullKey() + " 注解拿到的value:" + redisCacheBean.getRedisValue() + " redis删除结果:" + isDel);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
