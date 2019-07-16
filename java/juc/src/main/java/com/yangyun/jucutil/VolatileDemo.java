package com.yangyun.jucutil;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName VolatileDemo
 * @Description: 同步锁锁的对象, 每次访问都会去刷新主内存中的数据
 *      volatitl: 当多个线程操作共享数据时, 可以保证线程之间共享数据的可见性, 也就是当一个线程改变了共享数据的值
 *                 ,其他线程可以立马感知到
 *                 相较于 synchronized 是一种轻量级同步策略
 *      注意:
 *          1. volatile 不具备 "互斥性" 也就是多个线程可以同时访问
 *          2. volatile 不保证 "原子性"
 *      i++ 原子性问题: i++ 的操作实际分为三个步骤: "读-改-写"
 *      int i = 10;
 *      i = i++; // i =10;
 *      ① 创建临时变量并赋值 int temp = i;
 *      ② 执行加操作 i = i +1;
 *      ③ 将临时变量赋值 i = temp;
 *
 * @Author 86155
 * @Date 2019/6/28 22:13
 * @Version 1.0
 **/
public class VolatileDemo {

    public static void main(String[] args) throws InterruptedException {
        // volatile 可见性
//        volatileVisible();
        int i = 10;
//        i = i++;
        System.out.println(i++);
    }

    public static void volatileVisible() {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        while (true) {
            synchronized (td){
                if (td.isFlag()){
                    System.out.println("-----------flag \t" + td.isFlag());
                    break;
                }
            }

        }
    }
}

class ThreadDemo implements Runnable {

    @Getter
    @Setter
//    volatile private boolean flag = false;
     private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag \t" + isFlag());
    }
}
