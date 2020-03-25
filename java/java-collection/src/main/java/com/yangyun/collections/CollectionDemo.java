package com.yangyun.collections;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CollectionDemo {

    private static final String STR = "Lq9BH,MR8aH,MQXAg,MQXBH,MR9Ag,N1wAg,N1wBH,N2WaH,LpXBH,N38aH,N39BH,N39Ag,N2XBH,LowAg,LpXAg,LpWaH,LowBH,Lq9Ag,MPwAg,MPvaH,MPwBH";

    public static void main(String[] args) {
        // hashmap
//        testHashMap();
        Map<Object, Object> map = Collections.synchronizedMap(new HashMap<>());
        map.put(1, 1);

        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();

        while(iterator.hasNext()){

        }

        Hashtable<Object, Object> table = new Hashtable<>();
//        table.put()


        // treemap
//        String k1 = null;
//        SortedMap<String, String> sortedMap = new TreeMap<>();
//        sortedMap.put(null, null);
//        Comparable<? super String> comparable = k1;
//        CollectionDemo cd = new CollectionDemo();
//        cd.compare("a", null);
    }

    private static void testHashMap() {
        Map<String, String> map = new ConcurrentHashMap<>();
        String[] split = STR.split(",");
        for (int i = 0; i < split.length; i++){
            map.put(split[i], split[i]);
        }
        map.put("a", "a");
        String a = map.remove("a");
    }

    final int compare(Object k1, Object k2) {
        Comparable<? super String> k11 = (Comparable<? super String>) k1;
        return comparator==null ? k11.compareTo((String)k2)
                : comparator.compare((String)k1, (String)k2);
    }
    private final Comparator<? super String> comparator = null;
}
