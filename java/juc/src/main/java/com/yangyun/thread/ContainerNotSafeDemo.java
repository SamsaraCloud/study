package com.yangyun.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description 集合类不安全问题
 *  ArrayList 线程不安全
 * @author yangyun
 * @date 2019/6/14
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        List<String> list2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++){
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }).start();
        }

        // java.util.ConcurrentModificationException 并发修改异常 concurrentmodificationexception
        /**
         * 1 故障 ConcurrentModificationException
         *
         * 2 产生原因
         *   在多线程环境下, 一个线程在进行写操作时, 另一个线程进行读操作, 造成数据不一致
         *
         * 3 解决方法
         *   3.1 使用 Vector
         *   3.2 使用 Collections.synchronizeList(new ArrayList)
         *   3.3 new CopyOnWriteArrayList 写时复制, 读写分离
         *
         * 4 优化建议
         *
         *
         *
         */
    }
}
