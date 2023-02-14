package com.tuling.jucdemo.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Fox
 * 等待唤醒机制 await/signal测试
 */
@Slf4j
public class ConditionTest2 {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private int amount = 0;

    public void method1(String name){
        System.out.println(name + "付钱线程-启动");
        lock.lock();
        try {
            System.out.println(name + "付钱线程-获取锁");
            Thread.sleep(3000);
            while (amount <= 1){
                System.out.println(name + "付钱线程-释放锁，阻塞进入等待前的金额" + amount);
                condition.await();//线程会释放当前占用的锁,并进入等待
                System.out.println(name + "付钱线程-被唤醒后的金额" + amount);
            }
            amount -= 2;
            System.out.println(name + "付钱线程-支付两块钱");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        System.out.println(name + "离开-付钱线程，并解锁");
        lock.unlock();
    }

    public void method2(String name){
        System.out.println(name + "充钱线程-启动");
        lock.lock();
        try {
            System.out.println(name + "充钱线程-获取锁");
            Thread.sleep(500);
            amount += 1;
            System.out.println(name + "充钱线程-充一块钱" + amount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "充钱线程-随机唤醒等待队列中的一个-付钱线程");
        condition.signal();//随机唤醒等待队列中的一个线程
        System.out.println(name + "离开充钱线程，并解锁");
        lock.unlock();
    }

    public static void main(String[] args) {
        ConditionTest2 c = new ConditionTest2();
        Thread t  =new Thread(new Runnable() {
            @Override
            public void run() {
                c.method1("付钱测试1-");
            }
        });
        Thread t2  =new Thread(new Runnable() {
            @Override
            public void run() {
                c.method1("付钱测试2-");
            }
        });
        Thread t3  =new Thread(new Runnable() {
            @Override
            public void run() {
                c.method2("充值测试3-");
            }
        });
        Thread t4  =new Thread(new Runnable() {
            @Override
            public void run() {
                c.method2("充值测试4-");
            }
        });
        t.start();
        t2.start();
        t3.start();
        t4.start();
    }

}
