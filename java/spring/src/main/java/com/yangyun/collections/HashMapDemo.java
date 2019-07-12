package com.yangyun.collections;


import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();

        map.put("a", "a");
        map.put("b", "a");
        map.put("c", "a");
        map.put("d", "a");
        map.put("e", "a");
        map.put("f", "a");
        map.put("g", "a");
        map.put("h", "a");
        map.put("i", "a");
        map.put("j", "a");
        map.put("k", "a");
        map.put("l", "a");
        map.put("m", "a");
        String put = map.put("m", "a");
        System.out.println(map);
    }


}
