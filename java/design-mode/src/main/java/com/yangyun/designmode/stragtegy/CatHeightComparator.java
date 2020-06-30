package com.yangyun.designmode.stragtegy;

/**
 * @ClassName CatHeightComparator
 * @Description:
 * @Author 86155
 * @Date 2020/6/30 18:44
 * @Version 1.0
 **/
public class CatHeightComparator implements Comparator<Cat> {

    @Override
    public int compare(Cat o1, Cat o2) {
        if (o1.getHeight() > o2.getHeight()){
            return -1;
        } else if (o1.getHeight() < o2.getHeight()){
            return 1;
        }
        return 0;
    }
}
