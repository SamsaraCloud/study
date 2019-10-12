package com.yangyun.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @description 阻塞队列
 * @author yangyun
 * @date 2019/7/12 0012
 */
public class BlockQueueDemo {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> abq =
                new ArrayBlockingQueue<>(5, false);


    }
}

class ShareData<String> {
    private BlockingQueue<String> q = null;

    public ShareData (BlockingQueue<String> q){
        this.q = q;
    }

    public void add (){

    }
}
