package com.yangyun.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName BlockingQueueDemo
 * @Description: 阻塞队列
 * @Author 86155
 * @Date 2019/6/26 0:05
 * @Version 1.0
 **/
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5, false);
    }
}
