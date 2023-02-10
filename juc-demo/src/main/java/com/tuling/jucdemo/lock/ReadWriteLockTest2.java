package com.tuling.jucdemo.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Fox
 */
@Slf4j
public class ReadWriteLockTest2 {
    private Object data;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock r = readWriteLock.readLock();
    private Lock w = readWriteLock.writeLock();

    private void read() {
        log.debug("准备获取读锁....");
        r.lock();
        try {
            // TODO 业务逻辑
            log.debug("获取读锁,读取数据...");
            data = "read";
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            r.unlock();
            log.debug("释放读锁...");
        }
    }

    private void write() {
        log.debug("准备获取写锁....");
        w.lock();
        try {
            // TODO 业务逻辑
            log.debug("获取写锁,写入数据...");
            data = "write";
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            w.unlock();
            log.debug("释放写锁...");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockTest2 test = new ReadWriteLockTest2();
        // 测试读读，读写，写读，写写场景
        new Thread(() -> {
            test.read();
        }, "thread1").start();

        Thread.sleep(10);

        new Thread(() -> {
            test.write();
        }, "thread2").start();

    }

}
