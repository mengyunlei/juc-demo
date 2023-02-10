package com.tuling.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public  class TaskWithRetryFuture<V> implements RunnableScheduledFuture<V>{



    private RunnableScheduledFuture<V> runnableScheduledFuture;


    private TaskExecuteInfo taskExecuteInfo;


    public TaskWithRetryFuture(TaskExecuteInfo taskExecuteInfo,RunnableScheduledFuture<V> runnableScheduledFuture) {
        this.taskExecuteInfo = taskExecuteInfo;
        this.runnableScheduledFuture = runnableScheduledFuture;
    }


    @Override
    public void run() {
        runnableScheduledFuture.run();
        print("任务已跑%s次", taskExecuteInfo.getExecuteCount());
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return runnableScheduledFuture.getDelay(unit);
    }

    @Override
    public int compareTo(Delayed o) {
        return runnableScheduledFuture.compareTo(o);
    }

    @Override
    public boolean isPeriodic() {
        return runnableScheduledFuture.isPeriodic();
    }

    public TaskExecuteInfo getTaskExecuteInfo() {
        return taskExecuteInfo;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return runnableScheduledFuture.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return runnableScheduledFuture.isCancelled();
    }

    @Override
    public boolean isDone() {
        return runnableScheduledFuture.isDone();
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return runnableScheduledFuture.get();
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return runnableScheduledFuture.get(timeout, unit);
    }


    private void print(String format, Object ... args) {
        String msg = String.format(format, args);
        String date = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date());
        System.out.println(date + " "+msg);
    }

}
