package com.yangyun.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * @author yangyun
 * @Description:
 * @date 2021/3/1 20:11
 */
public class BigDecimalUtils {

    /**
     * 功能描述: a 是否大于等于 b
     * @param a：
     * @param b：
     * Return: java.lang.Boolean
     * Author: yun.Yang
     * Date: 2021/3/1 20:16
     */
    public static Boolean compareCapital (BigDecimal a, BigDecimal b){
        return a.compareTo(b) >= 0;
    }


    /**
     * 功能描述: 进一制，保留有效位数后进一5.243->5.25  5.219->5.22
     * @param newScale：有效位数
     * Return: java.math.BigDecimal
     * Author: yun.Yang
     * Date: 2021/3/1 11:52
     */
    public static BigDecimal roundingUp (BigDecimal b, int newScale){
        if (Objects.isNull(b)){
            return BigDecimal.ZERO;
        }
        return b.setScale(newScale, BigDecimal.ROUND_UP);
//        MathContext m = new MathContext(precision, RoundingMode.UP);
//        return b.abs(m);
    }

    public static void main(String[] args) {
        System.out.println(compareCapital(new BigDecimal(3.21), new BigDecimal(3.22)));
    }
}
