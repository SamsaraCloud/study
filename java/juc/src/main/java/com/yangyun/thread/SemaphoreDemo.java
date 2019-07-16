package com.yangyun.thread;

import java.util.concurrent.Semaphore;

/**
 * @ClassName SemaphoreDemo
 * @Description: 多个线程争抢多个资源, 线程数 > 资源数
 * @Author 86155
 * @Date 2019/6/25 23:06
 * @Version 1.0
 **/
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 设置可资源池大小 3, 每次允许获取资源最大线程数为 3, 可重复使用
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread (() -> {
                try {
                    semaphore.acquire();// 该线程获取资源池中的一份资源
                    System.out.println(Thread.currentThread().getName() + "\t 获得了一份资源, 并在使用中.....");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "\t 释放资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // 资源使用完, 释放
                }
            }, String.valueOf(i)).start();
        }
    }
}
