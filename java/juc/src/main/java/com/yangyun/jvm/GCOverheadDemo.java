package com.yangyun.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GCOverheadDemo
 * @Description: java.lang.OutOfMemoryError: GC overhead limit exceeded
 * @Author 86155
 * @Date 2019/8/20 21:56
 * @Version 1.0
 **/
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e){
            System.out.println("i=======" + i);
            e.printStackTrace();
            throw e;
        }
    }
}
