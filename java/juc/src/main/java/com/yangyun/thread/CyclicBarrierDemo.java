package com.yangyun.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName CyclicBarrierDemo
 * @Description: 循环阻塞固定大小个数线程, 直到满足大小, 才可继续执行
 * @Author 86155
 * @Date 2019/6/24 23:27
 * @Version 1.0
 **/
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        // 第一个参数: 阻塞线程数; 第二个参数: 达到阻塞线程数后执行的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("我终于可以执行了");
        });

        for (int i = 0; i < 8; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println("我是第"+tempInt+"号阻塞的线程");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
