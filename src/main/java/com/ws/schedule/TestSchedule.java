package com.ws.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试quartz
 */
public class TestSchedule {

    public static void main(String[] args) throws Exception {

        StdSchedulerFactory factory = new StdSchedulerFactory();
        factory.initialize();
        // TEST-WS
        Scheduler scheduler = factory.getScheduler();

        // 绑定Job,设置名称以及组
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("my-job", "WS").build();
        System.out.println(jobDetail);
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)//每隔1s执行一次
                        .repeatForever()).build();//一直执行

        scheduler.scheduleJob(jobDetail, trigger);

        scheduler.start();

        LockSupport.park();
    }

}
