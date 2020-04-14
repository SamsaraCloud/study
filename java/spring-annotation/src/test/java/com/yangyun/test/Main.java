package com.yangyun.test;

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
