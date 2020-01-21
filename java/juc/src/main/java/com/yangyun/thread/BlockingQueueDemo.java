package com.yangyun.thread;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlockingQueueDemo
 * @Description: 阻塞队列
 * @Author 86155
 * @Date 2019/6/26 0:05
 * @Version 1.0
 **/
public class BlockingQueueDemo {

    public static void main(String[] args) throws Exception {
        BlockingQueue<String> arry = new ArrayBlockingQueue<String>(5, false);

//        arry.offer("aa");
//        arry.offer("aa");
        arry.add("aaa");
        arry.add("bbb");
        arry.add("ccc");
        arry.add("ddd");
        arry.add("eee");
//        arry.put("");
        Iterator<String> before = arry.iterator();

        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());

        before = arry.iterator();

        System.out.println("-----------------改变后-----------------------");
        while (before.hasNext()){
            System.out.println(before);
            System.out.println(before.next());
        }

//        arry.take();



//        new Thread(() -> {
//            for (int i = 0; ; i ++){
//                try {
//                    System.out.println("生产者" + String.valueOf(i));
//                    arry.put(String.valueOf(i));
////                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "生产者").start();

//        new Thread(() -> {
//            try {
//                for (int i = 0; ; i++) {
//                    arry.put(String.valueOf(i));
//                }
//            } catch (Exception e){
//
//            }
//        }, "生产者");
//
//
//        new Thread(() -> {
//            try {
//                while (true){
//                    System.out.println("消费者====" + arry.take());
//                }
//            } catch (Exception e){
//
//            }
//        }, "消费者").start();
    }
}
