package com.yangyun.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MyThreadPoolDemo
 * @Description: 连接池
 * @Author 86155
 * @Date 2019/7/17 22:01
 * @Version 1.0
 **/
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        // 一池固定处理线程, 可控制并发数, 超出任务会在队列中等待
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 一池固只有一个线程, 保证所有任务按顺序执行
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        // 一池可扩展多线程, 适用很多短期异步任务, 可缓存线程池, 可灵活回收空闲线程或创建新线程
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 1; i <= 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 正在执行任务");
                });

//                TimeUnit.MILLISECONDS.sleep(2000);
            }

        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }
    }
}
