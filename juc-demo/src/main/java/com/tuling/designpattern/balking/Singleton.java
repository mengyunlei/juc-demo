package com.tuling.designpattern.balking;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Fox
 */
public class Singleton {
    private static volatile Singleton singleton;

    //构造方法私有化
    private Singleton() {}

    //获取实例（单例）
    public static Singleton getInstance() {
        //第一次检查
        if (singleton == null) {
            synchronized (Singleton.class) {
                //获取锁后二次检查
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}