package com.tuling.jucdemo.sync;

import com.tuling.schedule.ScheduleRetryThreadPoolTaskExecutor;
import com.tuling.schedule.TaskExecuteInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
public class ObjectTest2 {
    static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(100);
    static RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, workQueue, handler);

    public static void main(String[] args) throws InterruptedException{
        Vector<String> list = new Vector<>();
        String[] strs = {"a","b","c","d","e"};
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(String str : strs){
            executor.execute(()->doTask(str,list,countDownLatch));
        }
        countDownLatch.await();
        System.out.println(list.toString());
    }

    public static void doTask(String str, List<String> list,CountDownLatch countDownLatch){
        try{
            list.add(str);
        }finally {
            countDownLatch.countDown();
        }

    }



}

