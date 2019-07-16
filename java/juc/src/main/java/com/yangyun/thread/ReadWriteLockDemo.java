package com.yangyun.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName ReadWriteLockDemo
 * @Description: 在多高并发程序中, 要满足多个线程能够同时读取同一个资源类
 *               但是有一个线程去写这个资源类的时候, 其他线程是不能对其读写的
 *               也就是: 读-读 共存, 读-写 不能共存, 写-写 不能共存
 * @Author 86155
 * @Date 2019/6/23 22:15
 * @Version 1.0
 **/
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        MyCache cache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int a = i;
            new Thread(() -> {
                cache.put(String.valueOf(a), a);
            }, String.valueOf(i)).start();
        }


        for (int i = 1; i <= 5; i++) {
            final int a = i;
            new Thread(() -> {
                cache.get(String.valueOf(a));
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * @Author yangyun
 * @Description:  自定义缓存
 * @Date 2019/6/23 22:20
 * @Param
 * @returnm
 **/
class MyCache {
    // 在数据删改查时, 保证对所有线程可见
    private volatile Map<String, Object> map = new HashMap<>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put (String key, Object value){
        lock.writeLock().lock();

        try {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + "\t 正在写入" + key);
            thread.sleep(300);
            map.put(key, value);
            System.out.println(thread.getName() + "\t 写入完成");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void get (String key){
        lock.readLock().lock();

        try {
            Object value;
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + "\t 正在读取");
            thread.sleep(300);
            value = map.get(key);
            System.out.println(thread.getName() + "\t 读取完成" + value);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

}