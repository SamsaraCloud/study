package com.yangyun.designmode.stragtegy;

/**
 * @ClassName Sorter
 * @Description:
 * @Author 86155
 * @Date 2020/6/30 16:55
 * @Version 1.0
 **/
public class Sorter<T> {

    public void sort(T[] arr, Comparator c){
        for (int i = 0; i < arr.length - 1; i++){
            int minPos = i;
            for (int j = i +1; j < arr.length; j++){
                // 升序，拿前一个和 arr[i-1] 一次比较，得到最小值
                minPos = c.compare(arr[j], arr[minPos]) > -1 ? j : minPos;
            }
            swap(arr, i, minPos);
        }
    }

    public void swap(T[] arr, int i, int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
