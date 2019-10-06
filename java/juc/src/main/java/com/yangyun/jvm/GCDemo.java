package com.yangyun.jvm;

import java.util.Random;

/**
 * @ClassName GCDemo
 * @Description: 垃圾收集器使用
 * 新生代
 * 1. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC   (DefNew+Tenured)
 * 2. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC   (DefNew+Tenured)
 *  说明: Java Hotspot(TM) 64-Bit Server VM warning
 *      Using the ParNew young collector with the Serial old collector is deprecated
 *      and will likely be removed in a future release
 * 3. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC   (PSYoungGen+ParOldGen)
 * 老年代
 * 4.
 *  4.1 -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC   (PSYoungGen+ParOldGen)
 *  4.2 没配置垃圾收集器默认 UseParallelGC -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags     (PSYoungGen+ParOldGen)
 * 5. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC    (ParNew + concurrent mark sweep + Serial Old)
 * 6. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC
 * 7. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialOldGC (实际java8已经被优化掉了)
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
