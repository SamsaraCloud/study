package com.yangyun.jucutil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName SyncAndReentrantLockDemo
 * @Description:
 * synchronized 和 lock 有什么区别, 新的 lock 有什么好处
 *  1. synchronized 时关键字, 属于 jvm 层面的
 *     底层由 monitorenter(进入) 和 monitorexit(退出, 有两个, 一个正常, 一个异常) 实现
 *     wait 和 notify 等方法只能在 synchronized 同步代码块中才能使用
 *     Lock 是具体的类 java.util.concurrent.locks.Lock 是 api 层面的锁
 *  2. 使用方法
 *      synchronized 不需要用户手动释放锁, 程序执行完, 自动释放
 *      ReentrantLock 需要用户手动释放锁, lock(), unlock(): 成对出现; 如果没有主动释放锁, 有可能导致死锁
 *  3. 等待是否可中断
 *      synchronized 不可中断, 要么正常执行结束, 要么异常退出
 *      ReentrantLock 可中断, 1. 设置超时方法 tryLock(long timeout, TimeUnit unit)
 *                            2. lockInterruptibly() 放代码块中, 调用 interrupt()可中断
 *  4. 枷锁是否公平
 *      synchronized 非公平锁
 *      ReentrantLock 默认非公平锁, 构造方法可以传入 boolean 值,  true 公平, false 非公平
 *  5. 锁绑定多个条件 Condition
 *      synchronized 没有
 *      ReentrantLock 用来实现分组唤醒需要唤醒的线程, 可以做到精确唤醒
 *      synchronized 要么唤醒一个, 要么唤醒全部
 * @Author 86155
 * @Date 2019/7/15 21:08
 * @Version 1.0
 **/
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResouce shareResouce = new ShareResouce();

        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResouce.print5();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResouce.print10();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++) {
                shareResouce.print15();
            }
        }, "CC").start();
    }
}

class ShareResouce {
    private int number = 1; // 1 A 2 B 3 C
    private Lock lock = new ReentrantLock();
    private Condition a = lock.newCondition();
    private Condition b = lock.newCondition();
    private Condition c = lock.newCondition();


    public void print5 (){
        try {
            lock.lock();
            // 判断
            while (number != 1){
                a.await();
            }

            // 干活
            for (int i = 1; i <= 5; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 2;
            b.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10 (){
        try {
            lock.lock();
            // 判断
            while (number != 2){
                b.await();
            }

            // 干活
            for (int i = 1; i <= 10; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 3;
            c.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15 (){
        try {
            lock.lock();
            // 判断
            while (number != 3){
                c.await();
            }

            // 干活
            for (int i = 1; i <= 15; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            number = 1;
            a.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
