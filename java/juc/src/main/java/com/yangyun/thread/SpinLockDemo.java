package com.yangyun.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yangyun
 * @create 2019-06-22-11:22
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock (){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in");

        while (!atomicReference.compareAndSet(null, thread)){

        }
    }

    public void myUnLock (){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t invoked");
        atomicReference.compareAndSet(thread, null);
    }

    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(() -> {
            spinLockDemo.myLock();
            // 业务代码处理
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放锁
            spinLockDemo.myUnLock();
        }, "AA").start();

        Thread.sleep(1000);

        new Thread(() -> {
            spinLockDemo.myLock();

            spinLockDemo.myUnLock();
        }, "BB").start();
    }
}
