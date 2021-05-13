package com.yangyun.utils;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author yangyun
 * @Description:
 * @date 2021/3/12 18:23
 */
public class DateUtils {

    /**
     * 功能描述: 计算时间相差分钟数
     * @param start：
     * @param end：
     * Return: long
     * Author: yun.Yang
     * Date: 2021/3/10 0:23
     */
    public static long diffMin (LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end).toMinutes();
    }

    /**
     * 功能描述: 根据传入时间获取当月第一天
     * @param now： LocalDateTime @see (-1)
     * @see LocalDateTime#plusMonths 获取当前时间上n月或下n月
     * Return: java.time.LocalDateTime
     * Author: yun.Yang
     * Date: 2021/3/12 18:26
     *
     */
    public static LocalDateTime getCurrentMonthFirstDay (LocalDateTime now){
        LocalDateTime first = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime of = LocalDateTime.of(first.getYear(), first.getMonth(), first.getDayOfMonth(), 00, 00, 00);
        return of;
    }

    /**
     * 功能描述: 根据传入时间获取当月最后一天
     * @param now： LocalDateTime @see (-1)
     * @see LocalDateTime#plusMonths 获取当前时间上n月或下n月
     * Return: java.time.LocalDateTime
     * Author: yun.Yang
     * Date: 2021/3/12 18:26
     *
     */
    public static LocalDateTime getCurrentMonthLastDay (LocalDateTime localDateTime){
        LocalDateTime first = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime of = LocalDateTime.of(first.getYear(), first.getMonth(), first.getDayOfMonth(), 23, 59, 59);
        return of;
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMonthFirstDay(LocalDateTime.now()));
    }
}
