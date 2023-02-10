package com.tuling.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class ScheduleRetryThreadPoolTaskExecutor extends ScheduledThreadPoolExecutor {


    /**
     * @param corePoolSize     线程池核心数
     * @param taskTryMaxTimes  任务最大尝试次数
     * @param taskDelaySeconds
     * @param multiplier       If positive, then used as a multiplier for generating the next delay for taskTryMaxTimes
     */
    public ScheduleRetryThreadPoolTaskExecutor(int corePoolSize, int taskTryMaxTimes, int taskDelaySeconds, int multiplier) {
        super(corePoolSize);
        if (taskTryMaxTimes < 1) {
            throw new IllegalStateException("taskTryMaxTimes should gt 0");
        }
        this.taskTryMaxTimes = taskTryMaxTimes;
        this.taskDelaySeconds = taskDelaySeconds;
        if (multiplier <= 0) {
            throw new IllegalStateException("multiplier should gt 0");
        }
        this.multiplier = multiplier;
    }


    /**
     * 任务最大重试次数
     */
    private int taskTryMaxTimes;
    /**
     * 任务首次尝试延迟时间(单位秒)
     */
    private int taskDelaySeconds;
    /**
     * multiplier If positive, then used as a multiplier for generating the next delay for taskTryMaxTimes
     */
    private int multiplier;

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        TaskWithRetryFuture<?> future = (TaskWithRetryFuture) r;
        TaskExecuteInfo taskExecuteInfo = future.getTaskExecuteInfo();
        boolean retryRequest = taskExecuteInfo.getExecuteCount() > 1;
        t = getThrowable(r, t);
        if (t == null) {
            if (retryRequest) {
                print("任务:%s在重试%s次后成功", taskExecuteInfo.getRunable(), taskExecuteInfo.getExecuteCount() - 1);
            }
            return;
        }
        if (retryRequest) {
            if (taskExecuteInfo.getExecuteCount() > taskTryMaxTimes) {
                print("任务:%s重试最大次数，最大次数为%s，最后错误为:%s", taskExecuteInfo.getRunable(), taskTryMaxTimes, t.getMessage());
                return;
            }
        } else {
            print("首次提交重试任务");
        }

        if (t instanceof Exception) {
            int delaySeconds = retryRequest ? taskExecuteInfo.getCurrentDelaySeconds() * multiplier
                    : taskExecuteInfo.getCurrentDelaySeconds();
            taskExecuteInfo.setCurrentDelaySeconds(delaySeconds);
            super.schedule(taskExecuteInfo, delaySeconds, TimeUnit.SECONDS);
        }

    }

    @Override
    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
        RunnableScheduledFuture<V> future = super.decorateTask(runnable, task);
        TaskWithRetryFuture<V> retryFuture = new TaskWithRetryFuture<V>((TaskExecuteInfo) runnable, future);
        return retryFuture;
    }


    private Throwable getThrowable(Runnable r, Throwable t) {
        if (t == null && r instanceof Future<?>) {
            try {
                Object result = ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        return t;
    }

    private void print(String format, Object ... args) {
        String msg = String.format(format, args);
        String date = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
        System.out.println(date + " "+msg);
    }

}