package com.yangyun.test;

import org.junit.Test;

import java.util.concurrent.locks.StampedLock;

/**
 * @ClassName JavaTest
 * @Description:
 * @Author 86155
 * @Date 2020/3/25 21:51
 * @Version 1.0
 **/
public class JavaTest {

    Integer  i = new Integer(244);

    public static String a = "";


    @Test
    public void test01(){
        System.out.println(a());
        StampedLock sl = new StampedLock();

    }

     public int a (){
        int a = 1;
        try {
            return a;
        } catch (Exception e){
            return a;
        }finally {
            a = 4;
            System.out.println(a);
        }
     }

}
