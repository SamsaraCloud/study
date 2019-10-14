package com.yangyun.jucutil;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @ClassName AtomicStampedReferenceDemo
 * @Description:
 * @Author 86155
 * @Date 2019/10/8 23:01
 * @Version 1.0
 **/
public class AtomicStampedReferenceDemo {

    public static void main(String[] args) {
        // initialRef 初始值  initialStamp 初始版本号
        AtomicStampedReference asr = new AtomicStampedReference(1, 5);
        System.out.println(asr.getReference());
        System.out.println(asr.getStamp());

        System.out.println("===============================");

        // expectedReference: 期望值 expectedStamp: 期望版本号 (和上次设定的值比较是否一致)
        // newReference: 新值 newStamp: 新版本号 和上次设定值比较返回 true, 则修改为新值和新的版本号
        asr.compareAndSet(1, 3, 5, 6);
        System.out.println(asr.getReference());
        System.out.println(asr.getStamp());
    }
}
