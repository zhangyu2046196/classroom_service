package com.longbei.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangyu
 * @description  mybatis配置类，可以修改配置的属性
 * @date 2020/3/10 21:36
 */
@MapperScan(value = "com.longbei.mapper")
@Configuration
public class MyBatisConfig {

    /**
     * mybatis属性注册ioc容器
     * @return
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);//开启驼峰命名规则
            }
        };
    }

}
