package com.longbei.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangy
 * @version 1.0
 * @description  定义一个执行的job
 * @date 2020/3/12 21:34
 *
 * 开发步骤：
 * 1、继承"IJobHandler"："com.xxl.job.core.handler.IJobHandler"；
 * 2、注册到Spring容器：添加"@Component"注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加"@JobHandler(value="自定义jobhandler名称")"注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 */
@JobHandler(value="jobFirstTest")
@Component
public class JobHandlerFirst extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("测试第一个xxl-job项目...");
        return SUCCESS;
    }

}
