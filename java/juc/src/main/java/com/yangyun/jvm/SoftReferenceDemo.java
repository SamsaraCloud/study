package com.yangyun.jvm;

import java.lang.ref.SoftReference;

/**
 * @ClassName SoftReferenceDemo
 * @Description:
 * @Author 86155
 * @Date 2019/8/19 21:16
 * @Version 1.0
 **/
public class SoftReferenceDemo {

    /**
     * @Author yangyun
     * @Description: 内存够用就保留, 不够就回收
     * @Date 2019/8/19 21:17
     * @Param []
     * @returnm void
     **/
    public static void softRefMemoryEnough (){
        Object o = new Object();
        SoftReference<Object> sf = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(sf.get());
        o = null;
        System.gc();
        /**
         * 内存足够的情况
         * java.lang.Object@27d6c5e0
         * java.lang.Object@27d6c5e0
         * null
         * java.lang.Object@27d6c5e0
         **/
        System.out.println(o); // 此时系统内存足够, o null
        System.out.println(sf.get()); // sf 为的值不为 null
    }

    /**
     * @Author yangyun
     * @Description:  配置 堆内存固定为 5m, 并创建一个超过 5m 的大对象, 认为制造OOM
     * @Date 2019/8/19 21:25
     * @Param []
     * @returnm void
     **/
    public static void softRefMemoryNotEnough (){
        Object o = new Object();
        SoftReference<Object> sf = new SoftReference<>(o);
        System.out.println(o);
        System.out.println(sf.get());
        o = null;

        try {
            byte b[] = new byte[30 * 1024 * 1024];
        } catch (Throwable t) {

        } finally {
            System.out.println(o);
            System.out.println(sf.get());
        }
    }

    public static void main(String[] args) {
//        softRefMemoryEnough();
        softRefMemoryNotEnough();
    }
}
