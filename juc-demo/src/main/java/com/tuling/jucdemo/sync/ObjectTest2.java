package com.tuling.jucdemo.sync;

import com.tuling.schedule.ScheduleRetryThreadPoolTaskExecutor;
import com.tuling.schedule.TaskExecuteInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class ObjectTest2 {

    public static void main(String[] args) throws InterruptedException,ExecutionException,SocketTimeoutException {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(10);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS, workQueue, handler);
        String value = "task";
        for(int i=0; i<20; i++) {
            try {
                executor.execute(()->doTask(value,1));
            } catch (RejectedExecutionException e) {
                System.out.println("#####################");
                log.error(e.getMessage(),e);
            }
        }

    }

    public static void doTask(String value,int i){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value);
    }



}

