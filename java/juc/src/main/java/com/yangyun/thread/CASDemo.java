package com.yangyun.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yangyun
 * @create 2019-06-04-20:55
 * CAS compareAndSet: 比较并交换
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

//        boolean b = atomicInteger.compareAndSet(6, 1016);
//        System.out.println(b + " current data: " + atomicInteger.get());
//
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println(andIncrement);
    }
}
