package com.yangyun.thread;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ClassName LinkedBlockingDequeDemo
 * @Description:
 * @Author yangyun
 * @Date 2019/12/29 0029 17:38
 * @Version 1.0
 **/
public class LinkedBlockingDequeDemo {

    public static void main(String[] args) {
        LinkedBlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>();
        // 添加
        blockingDeque.add("aa");
        blockingDeque.addFirst("aa");
        blockingDeque.addLast("aa");
        blockingDeque.offer("aa");
        blockingDeque.offerFirst("aa");
        blockingDeque.offerLast("aa");

        // 检索但不删除
        blockingDeque.element();
        blockingDeque.getFirst();
        blockingDeque.getLast();

        blockingDeque.remove();
    }
}
