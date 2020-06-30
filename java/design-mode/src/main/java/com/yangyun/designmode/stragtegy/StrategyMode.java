package com.yangyun.designmode.stragtegy;

import java.util.Arrays;

/**
 * @ClassName StrategyMode
 * @Description: 策略模式，java 中 Comparator 接口中 compare 方法
 * @Author 86155
 * @Date 2020/6/30 16:54
 * @Version 1.0
 **/
public class StrategyMode {

    public static void main(String[] args) {
        Cat[] a = {new Cat(30, 30), new Cat(15, 15), new Cat(20, 20), new Cat(4, 4),};
        Sorter<Cat> s = new Sorter<>();
        s.sort(a, new CatHeightComparator());
        System.out.println(Arrays.toString(a));
    }
}
