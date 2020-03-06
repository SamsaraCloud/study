package com.yangyun;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName com.yangyun.TestDemo
 * @Description:
 * @Author yangyun
 * @Date 2019/10/17 0017 10:10
 * @Version 1.0
 **/
public class TestDemo {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {

        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe o = (Unsafe)theUnsafe.get(null);
        System.out.println(o.compareAndSwapObject(theUnsafe, 1, theUnsafe, o));
    }

    public String getClassName (){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(stackTrace[2].getMethodName());

        return this.getClass().getTypeName();
    }
}
