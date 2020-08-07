package com.yangyun.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

    /**
     * @description 21 个相同hash, 值不同的 String, 用于测试 HashMap
     * @author yangyun
     * @date 2019/7/15 0015
     * @param null
     * @return
     */
    private static final String str = "Lq9BH,MR8aH,MQXAg,MQXBH,MR9Ag,N1wAg,N1wBH,N2WaH,LpXBH,N38aH,N39BH,N39Ag,N2XBH,LowAg,LpXAg,LpWaH,LowBH,Lq9Ag,MPwAg,MPvaH,MPwBH";

    /**
     * @description 获取字符串 hash
     * @author yangyun
     * @date 2019/7/15 0015
     * @param str
     * @return int
     */
    public static int getHash (String str){
        int h;
        return (h = str.hashCode()) ^ (h >>> 16);
    }

    // 定义一个标准 hash 值
    public static final int NORMAL_NUM = 73609753;

    /**
     * @description 获取相同 hash 值的 String
     * @author yangyun
     * @date 2019/7/15 0015
     * @param
     * @return void
     */
    public static void getSameHashCodeStr(){
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        int hash = 0;
        String s = "";
        while (true){
            s = RandomStringUtils.randomAlphanumeric(5);
            hash = getHash(s);
            if (NORMAL_NUM == hash){
                System.out.println(s + "=====" + hash);
                map.put(s, s);
            }

            if (map.size() > 20){
                break;
            }
        }
    }

    /**
     * 功能描述: TODO 数据库转java驼峰
     * @Param: [s]
     * @Return: java.lang.String
     * @Author: yangyun
     * @Date: 2020/8/7 14:28
     */
    public static String ExchangeStringB(String s) {
        char c[] = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 65 && c[i] <= 90) {
                c[i] = (char) (c[i] + 32);
                sb.append("_").append(c[i]);
            } else {
                sb.append(c[i]);
            }

        }
        return sb.toString();
    }
}
