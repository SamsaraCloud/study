package com.yangyun.util;

/**
 * @ClassName StringDemo
 * @Description:
 * @Author 86155
 * @Date 2019/10/9 21:46
 * @Version 1.0
 **/
public class StringDemo {
    public static void main(String[] args) {
        String a = new String("sa");
        String b = new String("sa");

        System.out.println(a == b.intern());
    }
}
