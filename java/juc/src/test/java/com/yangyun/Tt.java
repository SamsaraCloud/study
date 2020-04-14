package com.yangyun;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName Tt
 * @Description:
 * @Author 86155
 * @Date 2020/4/14 16:24
 * @Version 1.0
 **/
public class Tt {

    public static void main(String[] args) {
        BlockingQueue<String> arry = new ArrayBlockingQueue<String>(5, false);
        arry.add("aaa");
        arry.add("bbb");
        arry.add("ccc");
        arry.add("ddd");
        arry.add("eee");

// 第一种情况, 在取出元素前
        Iterator<String> before = arry.iterator();

        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());
        System.out.println("取出了一个元素: " + arry.remove());

// 第二种情况, 在取出元素后
      //  Iterator<String> before = arry.iterator();

        while (before.hasNext()){
            System.out.println(before);
            System.out.println(before.next());
        }
    }
}
