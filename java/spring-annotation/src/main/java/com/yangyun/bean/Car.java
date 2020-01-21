package com.yangyun.bean;

/**
 * @ClassName Car
 * @Description:
 * @Author 86155
 * @Date 2020/1/17 15:20
 * @Version 1.0
 **/
public class Car {

    public Car(){
        System.out.println("Car construct...");
    }

    public void destroy(){
        System.out.println("Car destroy...");
    }

    public void init (){
        System.out.println("Car init ...");
    }
}
