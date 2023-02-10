package com.tuling.jucdemo.sync;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ScheduledMultiThreadTool {
    private  static Integer count =1;
    MyTimerTask myTimerTask = new MyTimerTask();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);

    public void start(){
        try {
            //一秒执行一次
            scheduled.scheduleAtFixedRate(myTimerTask, 0,1, TimeUnit.SECONDS);
            while (!scheduled.isTerminated()){
                lock.readLock().lock();
                if (count >20){
                    scheduled.shutdown();
                }
                lock.readLock().unlock();

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
    }
    private class MyTimerTask implements Runnable {
        @Override
        public void run(){
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+" 第 "+count+ " 次执行任务,count="+count);
            count ++;
            lock.writeLock().unlock();
        }

    }

    public static void main(String[] args) {
        new ScheduledMultiThreadTool().start();
    }
}

