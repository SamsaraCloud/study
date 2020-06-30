package com.yangyun.designmode;

/**
 * @Author yangyun
 * @Description: 顶级比较接口，提供比较方法，只需重写，实现自定义比较策略
 * @Date 2020/6/30 18:39
 * @Param
 * @returnm
 **/
public interface Comparator<T> {
    int compare(T o1, T o2);
}
