package com.yangyun.collections;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description 阻塞队列
 * @author yangyun
 * @date 2019/7/12 0012
 */
public class BlockQueueDemo {

    public static void main(String[] args) {
//        ArrayBlockingQueue<String> abq =
//                new ArrayBlockingQueue<>(5, false);
//        Share s = new Share(true);
//        try {
//        for (int i = 1; i <= 15; i++){
//            TimeUnit.MILLISECONDS.sleep(10);
//            new Thread(() -> {
//                s.doSomethings();
//            }, String.valueOf(i)).start();
//        }
//
//
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        List<String> list = s.getList();
//
//        System.out.println("++++++++++++++++++");
//        list.stream().forEach( a -> {
//            System.out.println(a);
//        });


        ReentrantLock lock = new ReentrantLock(true);

        lock.lock();
        lock.unlock();

    }
}

class Share {
    private static Lock lock = null;

    private static int count = 5;

    public Share (Boolean b){
        lock = new ReentrantLock(b);;
    }

    @Getter
    private List<String> list = new ArrayList<>();

    public void doSomethings (){
        System.out.println(Thread.currentThread().getName());
        try {
            lock.lock();
            list.add(Thread.currentThread().getName());

            TimeUnit.SECONDS.sleep(count--);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
