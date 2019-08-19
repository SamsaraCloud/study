package com.yangyun.jvm;

/**
 * @ClassName StrongReference
 * @Description: 强引用
 * @Author 86155
 * @Date 2019/8/19 21:14
 * @Version 1.0
 **/
public class StrongReference {
    public static void main(String[] args) {
        Object a = new Object();// 默认 a 就是强引用
        Object b = a; // b 也是强引用
        a = null;
        System.gc();
        System.out.println(b);
    }
}

