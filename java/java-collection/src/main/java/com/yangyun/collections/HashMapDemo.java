package com.yangyun.collections;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapDemo {

    private static final String STR = "Lq9BH,MR8aH,MQXAg,MQXBH,MR9Ag,N1wAg,N1wBH,N2WaH,LpXBH,N38aH,N39BH,N39Ag,N2XBH,LowAg,LpXAg,LpWaH,LowBH,Lq9Ag,MPwAg,MPvaH,MPwBH";

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
//        String[] split = STR.split(",");
//        for (int i = 0; i < split.length; i++){
//            map.put(split[i], split[i]);
//        }
        map.put("a", "a");

        String a = map.remove("a");
        List<String> list = new ArrayList<>();

    }
}
