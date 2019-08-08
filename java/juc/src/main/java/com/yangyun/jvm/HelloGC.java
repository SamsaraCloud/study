package com.yangyun.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName HelloGC
 * @Description: jps -l 查看进程编号, jinfo -flag 参数名 进程编号 查看对应参数是否开启
 * @Author 86155
 * @Date 2019/8/8 21:34
 * @Version 1.0
 **/
public class HelloGC {

    public static void main(String[] args) {
        System.out.println("*************HelloGC");

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
