package com.yangyun.test;

import org.junit.jupiter.api.Test;

/**
 * @ClassName TestDemo
 * @Description:
 * @Author 86155
 * @Date 2019/10/14 21:00
 * @Version 1.0
 **/
public class TestDemo {

    @Test
    public void test01(){
        if (!aaa() && bb()){

        }
    }

    public boolean aaa(){

        return false;
    }

    public boolean bb() {
        System.out.println("bbbbbb");
        return true;
    }
}
