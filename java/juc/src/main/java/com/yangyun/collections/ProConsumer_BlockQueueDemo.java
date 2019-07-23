package com.yangyun.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName ProConsumer_BlockQueueDemo
 * @Description:
 * @Author 86155
 * @Date 2019/7/15 22:52
 * @Version 1.0
 **/
public class ProConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource(new ArrayBlockingQueue<>(10));
        new Thread(() -> {
            try {
                shareResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(() -> {
            try {
                shareResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shareResource.stop();
    }
}

class ShareResource {
    private volatile boolean flag = true;
    private BlockingQueue<Integer> blockingQueue = null;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public ShareResource (BlockingQueue<Integer> blockingQueue){
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd () throws Exception {
        int data;
        boolean result;
        while (flag){
            data = atomicInteger.incrementAndGet();
            result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (result){
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据: " + data + ",成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列数据: " + data + ",失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName() + "\t 线程停止生产");
    }

    public void myConsumer () throws Exception {
        int data;
        Integer result;
        while (flag){
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);

            if (result == null){
                System.out.println(Thread.currentThread().getName() + "\t 获取队列数据失败");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 获取队列数据: " + result + ",成功");
        }
    }

    public void stop (){
        this.flag = false;
    }
}
