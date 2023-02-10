package com.tuling.jucdemo.sync;

import com.tuling.schedule.ScheduleRetryThreadPoolTaskExecutor;
import com.tuling.schedule.TaskExecuteInfo;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.net.SocketTimeoutException;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class ObjectTest1 {

    public static void main(String[] args) throws InterruptedException,ExecutionException,SocketTimeoutException {
        ScheduleRetryThreadPoolTaskExecutor retryThreadPoolTaskExecutor  =
                new ScheduleRetryThreadPoolTaskExecutor(3, 5, 3,1);

        int successTimes = 2;
        retryThreadPoolTaskExecutor.execute(
                new TaskExecuteInfo(()-> {
                    Random random = new Random();
                    int number = random.nextInt(8);
                    if (number != successTimes) {
                        log.info("执行失败,number:"+number);
                        throw new RuntimeException("我就是报错信息，哈哈哈");
                    }
                    log.info("执行成功");
                },3)
        );
        System.out.println("00000000000000");
        Thread.sleep(500);
        System.out.println("11111111111111");
        Thread.sleep(500);
        System.out.println("22222222222222");
    }



}

