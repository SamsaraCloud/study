package com.yangyun.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadMethodDemo {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        thread.dumpStack();

        Map<String, String> map = new HashMap<>();
        map.put("aa", "aa");
        System.out.println(map.containsKey("aa"));
    }

    public void write (){

    }
}
