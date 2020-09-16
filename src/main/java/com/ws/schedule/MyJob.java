package com.ws.schedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.TimeUnit;

//@DisallowConcurrentExecution
public class MyJob implements Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            System.out.println(Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(600L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
