package com.yangyun.collections;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ProdConsumer_TraditionDemo
 * @Description: 传统生产消费者
 * @Author 86155
 * @Date 2019/7/15 20:34
 * @Version 1.0
 **/
public class ProdConsumer_TraditionDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        shareData.increment();

//        new Thread(() -> {
//            for(int i=1; i<= 10; i++){
//                shareData.increment();
//            }
//        }, "aa").start();
//        new Thread(() -> {
//            for(int i=1; i<= 10; i++){
//                shareData.decrement();
//            }
//        }, "bb").start();
//        new Thread(() -> {
//            for(int i=1; i<= 10; i++){
//                shareData.increment();
//            }
//        }, "cc").start();
//        new Thread(() -> {
//            for(int i=1; i<= 10; i++){
//                shareData.decrement();
//            }
//        }, "dd").start();

    }
}

class ShareData {// 资源类, 多线程环境下
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment (){
        try {
            lock.lock();
            while (number !=0){

                condition.await();

            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement (){
        try {
            // 占有锁
            lock.lock();
            while (number ==0){
                // 等待
                condition.await();
            }
            // 操作
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            // 唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            lock.unlock();
        }
    }
}
