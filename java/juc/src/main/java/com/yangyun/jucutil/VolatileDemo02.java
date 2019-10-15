package com.yangyun.jucutil;

import lombok.Getter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName VolatileDemo02
 * @Description:
 * @Author 86155
 * @Date 2019/10/8 21:57
 * @Version 1.0
 **/
public class VolatileDemo02 {

    public static void main(String[] args) {
        ShareData sd = new ShareData();
        ShareResource sr = new ShareResource();








        for (int i = 0; i <100; i++){
//            new Thread(sr).start();
            sd.add();

            new Thread(() -> {
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println(sr.getA());
        System.out.println(sd.getAi().get());
    }

}

class ShareResource implements Runnable {

    @Getter
    volatile private int a = 0;

    @Override
    public void run() {
        a++;
    }
}

class ShareData {
    @Getter
    private AtomicInteger ai = new AtomicInteger(0);

    public void add (){
//        ai.incrementAndGet();
        ai.getAndIncrement();
//        System.out.println();
    }
}
