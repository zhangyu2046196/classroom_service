package com.longbei.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author zhangyu
 * @description spEL表达式工具类
 * @date 2020/3/11 12:03
 */
@Slf4j
public class SpelUtil {

    /**
     * 解析spel表达式
     *
     * @param spel
     * @param joinPoint
     * @return
     */
    public static Object getSpelValue(String spel, JoinPoint joinPoint) {
        try {
            //调整处理方式 ， 满足jdk7的动态代理 取不到
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parr = methodSignature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            if (parr == null) {
                //表明1.8以下  走的jdk动态代理过来的
                String classType = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                // 参数值
                LocalVariableTableParameterNameDiscoverer pnd = new LocalVariableTableParameterNameDiscoverer();
                // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
                Method method = Class.forName(classType).getMethod(methodName, ((MethodSignature) joinPoint.getSignature()).getParameterTypes());
                // 参数名
                parr = pnd.getParameterNames(method);
            }
            if (parr != null) {
                //使用SPEL进行key的解析
                ExpressionParser parser = new SpelExpressionParser();
                //SPEL上下文
                StandardEvaluationContext context = new StandardEvaluationContext();
                //把方法参数放入SPEL上下文中
                for (int i = 0; i < parr.length; i++) {
                    context.setVariable(parr[i], args[i]);
                }
                Object obj = parser.parseExpression(spel).getValue(context, Object.class);
                return obj;
            }

        } catch (Exception e) {
            log.error("解析spel表达式异常", e);
        }

        return null;
    }

    /**
     * 解析spel表达式
     *
     * @param spel
     * @param joinPoint
     * @param clazz
     * @return
     */

    public static Object getSpelValue(String spel, JoinPoint joinPoint, Class clazz) {
        Method method = SpelUtil.getMethod(joinPoint);
        if (method != null) {
            Object fieldValue = SpelUtil.paraseSpel(spel, Object.class, method, joinPoint.getArgs());
            return fieldValue;
        }

        return null;
    }

    /**
     * 解析spel表达式
     *
     * @param spel
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    public static Object paraseSpel(String spel, Class clazz, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        Object obj = parser.parseExpression(spel).getValue(context, clazz);

        return obj;
    }

    /**
     * 获取被拦截方法对象
     *
     * @param joinPoint
     * @return
     */
    public static Method getMethod(JoinPoint joinPoint) {

        Method method = null;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            method = methodSignature.getMethod();
        } catch (Exception e) {
            log.error("自定义注解获取被拦截方法对象异常", e);
        }
        return method;
    }

}
