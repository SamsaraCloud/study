package com.yangyun.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName CallableDemo
 * @Description: 实现多线程的另一种方式
 * 1. FutureTask 执行完, 可以通过 get 方法获取返回值
 * 2. 但是在使用 get 的时候, 如果线程任务还没有执行完, 会导致堵塞的情况
 * 3. 可以通过 isDone 方法判断线程是否已经结束
 * 4. 对于同一个 FutureTask 多个线程只会执行一次
 * @Author 86155
 * @Date 2019/7/16 22:38
 * @Version 1.0
 **/
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask task = new FutureTask(new MyThread());

        new Thread(task,"AA").start();

        System.out.println(task.get());

    }
}

class MyThread implements Callable {

    @Override
    public Object call() throws Exception {
        // 执行业务操作
        System.out.println(Thread.currentThread().getName() + "\t is starting");
        return "is done";
    }
}
