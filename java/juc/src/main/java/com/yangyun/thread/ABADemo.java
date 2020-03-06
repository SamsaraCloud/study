package com.yangyun.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author yangyun
 * @create 2019-06-13-22:00
 */
public class ABADemo { // ABA 问题解决 AtomicStampedReference

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        int stamp = atomicStampedReference.getStamp();
        atomicStampedReference.compareAndSet(100, 101, stamp, 1 + stamp);


        Map<String, String> map = new HashMap<>();
        map.put("a", "s");
    }
}
