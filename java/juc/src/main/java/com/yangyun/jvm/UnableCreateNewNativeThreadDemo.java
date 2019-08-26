package com.yangyun.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName UnableCreateNewNativeThreadDemo
 * @Description:
 * @Author 86155
 * @Date 2019/8/26 21:18
 * @Version 1.0
 **/
public class UnableCreateNewNativeThreadDemo {

    public static void main(String[] args) {
        for (int i = 1;  ; i++){
            System.out.println("============= " + i);
            new Thread(() ->{
                try {
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }, String.valueOf(i)).start();
        }
    }
}
