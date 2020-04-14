package com.yangyun.threadPool;

import java.util.concurrent.*;

/**
 * @ClassName MyThreadPoolDemo
 * @Description: 在项目中, 一般不会使用jdk 提供的线程池; 而是通过 ThreadPoolExecutor 手动实现
 *
 * @Author 86155
 * @Date 2019/7/23 22:11
 * @Version 1.0
 **/
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        CountDownLatch cd = new CountDownLatch(0);
        ExecutorService threadPool = new ThreadPoolExecutor(
                3,
                5,
                1L,
                 TimeUnit.SECONDS,
                 new LinkedBlockingDeque<Runnable>(3),
                 Executors.defaultThreadFactory(),
                 new ThreadPoolExecutor.AbortPolicy());
        System.out.println(Runtime.getRuntime().availableProcessors());

    }

}
