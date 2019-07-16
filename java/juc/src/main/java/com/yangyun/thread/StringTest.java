package com.yangyun.thread;

/**
 * @author yangyun
 * @create 2019-06-19-22:06
 */
public class StringTest {


    public static void main(String[] args) {
        String str1 = "abc";
        String str2 = new String("abc");
        System.out.println(str1 == str2);
        String str3 = new String("abc");
        System.out.println(str3 == str2);
    }
}
