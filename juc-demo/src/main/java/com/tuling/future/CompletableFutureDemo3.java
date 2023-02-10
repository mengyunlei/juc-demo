package com.tuling.future;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.concurrent.*;

@Slf4j
public class CompletableFutureDemo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException{
        long start =  System.currentTimeMillis();
        ExecutorService executor = Executors.newCachedThreadPool();
        HashMap map = new HashMap();
        CompletableFuture<Void> t1 = CompletableFuture.runAsync(()->{
            String s = task1();
            map.put("task1",s);
        },executor);
        CompletableFuture<Void> t2 = CompletableFuture.runAsync(()->{
            String s = task2();
            map.put("task2",s);
        },executor);
        CompletableFuture<Void> t3 = CompletableFuture.runAsync(()->{
            String s = task3();
            map.put("task3",s);
        },executor);
        CompletableFuture.allOf(t1,t2,t3).get();
        long end =  System.currentTimeMillis();
        log.info("耗时："+(end-start));
        executor.shutdown();
    }

    static String task1(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        return "100";
    }

    static String task2(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        return "200";
    }

    static String task3(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }
        return "300";
    }
}