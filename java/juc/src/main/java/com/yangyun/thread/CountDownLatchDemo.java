package com.yangyun.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName CountDownLatchDemo
 * @Description: 允许一个或多个线程等待, 直到其他线程执行的一组操作完成
 * @Author 86155
 * @Date 2019/6/24 23:11
 * @Version 1.0
 **/
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 我走了");
                countDownLatch.countDown(); // 数字减
            }, String.valueOf(i)).start();
        }

        countDownLatch.await(); // 当为 0 时, 线程通过

        System.out.println(Thread.currentThread().getName() + "\t 都走了, 我也走了");

    }
}
