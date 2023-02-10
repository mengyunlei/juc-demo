package com.tuling.schedule;

public class TaskExecuteInfo implements Runnable{
    private Runnable runable;
    private int executeCount;
    private int currentDelaySeconds;


    public TaskExecuteInfo(Runnable runable,int currentDelaySeconds) {
        super();
        this.runable = runable;
        this.executeCount = 0;
        this.currentDelaySeconds = currentDelaySeconds;
    }
    public Runnable getRunable() {
        return runable;
    }
    public void setRunable(Runnable runable) {
        this.runable = runable;
    }
    public int getExecuteCount() {
        return executeCount;
    }
    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }
    public void increaseCount() {
        this.executeCount++;
    }

    public int getCurrentDelaySeconds() {
        return currentDelaySeconds;
    }

    public void setCurrentDelaySeconds(int currentDelaySeconds) {
        this.currentDelaySeconds = currentDelaySeconds;
    }
    @Override
    public void run() {
        increaseCount();
        runable.run();
        System.out.println("执行第"+executeCount+"次");
    }
    @Override
    public String toString() {
        return "TaskExecuteInfo [runable=" + runable + ", executeCount=" + executeCount + ", currentDelaySeconds="
                + currentDelaySeconds + "]";
    }
}
