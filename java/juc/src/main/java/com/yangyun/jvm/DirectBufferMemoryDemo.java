package com.yangyun.jvm;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DirectBufferMemoryDemo
 * @Description: java.lang.OutOfMemoryError: Direct buffer memory
 * @Author 86155
 * @Date 2019/8/20 22:36
 * @Version 1.0
 **/
public class DirectBufferMemoryDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("配置的DirectMemorySize: " + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");

        TimeUnit.SECONDS.sleep(2);

        // 分配直接内存 10m, 设置程序启动启动时 MaxDirectMemorySize=5m
        // -Xms5m -Xmx5m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
    }
}
