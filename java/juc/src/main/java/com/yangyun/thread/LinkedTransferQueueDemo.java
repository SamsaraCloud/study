package com.yangyun.thread;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LinkedTransferQueueDemo
 * @Description:
 * @Author yangyun
 * @Date 2020/1/9 0009 16:14
 * @Version 1.0
 **/
public class LinkedTransferQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();
        transferQueue.add("a");
//
        transferQueue.offer("");
        transferQueue.offer("", 1, TimeUnit.SECONDS);

        transferQueue.peek();
        transferQueue.remove();

        transferQueue.take();
        transferQueue.poll();
//        transferQueue.poll(1, TimeUnit.SECONDS);

        transferQueue.transfer("b");


        System.out.println(transferQueue);
    }
}
