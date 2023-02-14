package com.tuling.jucdemo.sync;

import com.tuling.schedule.ScheduleRetryThreadPoolTaskExecutor;
import com.tuling.schedule.TaskExecuteInfo;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class ObjectTest2 {
    static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(100);
    static RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, workQueue, handler);

    public static void main(String[] args) throws InterruptedException,ExecutionException,SocketTimeoutException {

        String value = "task";
        for(int i=0; i<100; i++) {
            try {
                List<Integer> list = new ArrayList<>();
                executor.execute(()->doTask(value,1, list));
            } catch (RejectedExecutionException e) {
                log.error(e.getMessage(),e);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
        }

    }

    public static void doTask(String value,int i,List<Integer> list){
        int number = ThreadLocalRandom.current().nextInt(8);
        list.add(number);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(i>=5){
            throw new RuntimeException(Thread.currentThread().getName()+"卧槽，爆炸了~~~~~~~");
        }
        if (number != 2) {
            i++;
            doTask(value,i,list);
        }else{
            log.info(Thread.currentThread().getName()+"执行成功,执行次数:"+i+",list:"+ Arrays.toString(list.toArray()));
        }

    }



}

