package com.yangyun.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LinkedBlockingQueueDemo
 * @Description:
 * @Author yangyun
 * @Date 2020/1/8 0008 14:40
 * @Version 1.0
 **/
public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        // 添加元素
        queue.put("");
        queue.put("");
        queue.put("");
        queue.offer("");
        queue.offer("");
        queue.offer("", 1, TimeUnit.SECONDS);

        queue.remove();

        // 获取元素
        queue.take();
        queue.poll();
        queue.poll(1, TimeUnit.SECONDS);
    }
}
