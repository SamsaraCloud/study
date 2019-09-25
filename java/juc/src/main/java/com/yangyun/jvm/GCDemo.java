package com.yangyun.jvm;

import java.util.Random;

/**
 * @ClassName GCDemo
 * @Description: 垃圾收集器使用
 * 1. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC   (DefNew+Tenured)
 * 2. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC   (DefNew+Tenured)
 *  说明: Java Hotspot(TM) 64-Bit Server VM warning
 *      Using the ParNew young collector with the Serial old collector is deprecated
 *      and will likely be removed in a future release
 * 3. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSParallelGC   (PSYoungGen+ParOldGen)
 * 4.
 *  4.1 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSParallelOldGC   (PSYoungGen+ParOldGen)
 * @Author 86155
 * @Date 2019/9/25 22:21
 * @Version 1.0
 **/
public class GCDemo {
    public static void main(String[] args) {
        System.out.println("GC Demo..");
        try {
            String str = "yangyun";
            while (true){
                str += str + new Random().nextInt(7777777) + new Random(88888888);
                str.intern();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
