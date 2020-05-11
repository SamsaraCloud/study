package com.yangyun.test;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName Main
 * @Description:
 * @Author 86155
 * @Date 2020/4/12 12:48
 * @Version 1.0
 **/
public class Main {

    @Test
    public void test02(){
        List<String> list = new ArrayList<>();
        list = null;
        boolean b = CollectionUtils.sizeIsEmpty(list);
        System.out.println(b);
    }

    @Test
    public void test01(){

        long l3 = Integer.valueOf(10).longValue();

        LocalDateTime l1 = LocalDateTime.of(2020, 4, 28, 11, 31, 30);
        LocalDateTime l2 = LocalDateTime.of(2020, 4, 28, 12, 32, 00);

        Duration b = Duration.between(l1, l2);
        long l = b.toMillis();

        System.out.println(l);

        BigDecimal a = new BigDecimal(l);
        BigDecimal aa = new BigDecimal(1000 * 60 * 60);

        BigDecimal d = a.divide(aa, 2, RoundingMode.HALF_UP);

        System.out.println(d);

        System.out.println(d.setScale(0,BigDecimal.ROUND_UP));
    }

    public Emp setInfo (Emp e){
        if (e == null){
            return null;
        }
        return e;
    }



        public static void main (String args []){

            Scanner sc = new Scanner(System.in);
            List<String> list = new ArrayList<>();
            int c=0;
            while(sc.hasNextLine()){
                c ++;

                String curr = sc.nextLine();
                int cc = curr.length() % 8;
                int count = cc ==0 ? cc : 8-cc;
                StringBuffer sb = new StringBuffer(curr);
                while (count-- > 0){
                    sb.append("0");
                }
                String ss = sb.toString();
                for (int j = 0; j<ss.length()/8; j++){
                    list.add(ss.substring(j *8, (j+1)*8));
                    System.out.println(ss.substring(j *8, (j+1)*8));
                }
            }
        }

}
