package com.yangyun.jvm;

import java.lang.ref.WeakReference;

/**
 * @ClassName WeakReferenceDemo
 * @Description:
 * @Author 86155
 * @Date 2019/8/19 21:42
 * @Version 1.0
 **/
public class WeakReferenceDemo {
    public static void main(String[] args) {

        Object o = new Object();
        WeakReference<Object> wf = new WeakReference<>(o);
        o = null;
        System.gc();
        // 弱引用执行 gc 就会被回收
        System.out.println(wf.get());
    }
}
