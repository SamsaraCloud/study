package com.yangyun.thread;

/**
 * @author yangyun
 * @create 2019-06-02-16:18
 */
public class SingletonDemo {
    private static SingletonDemo instance = null;

    private SingletonDemo (){
        System.out.println(Thread.currentThread().getName() + "\t SingletonDemo ()");
    }

    public static SingletonDemo getInstance (){
        // DCL Double Check Lock 双端检索机制
        if (instance == null){
            synchronized (SingletonDemo.class) {
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }

}
