package com.yangyun.thread;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName DelayQueueDemo
 * @Description:
 * @Author yangyun
 * @Date 2019/12/23 0023 17:37
 * @Version 1.0
 **/
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedDemo> delayQueue = new DelayQueue<>();
//        delayQueue.
        DelayedDemo delayedDemo = new DelayedDemo();
        DelayedDemo delayedDemo2 = new DelayedDemo();
        delayQueue.add(delayedDemo);
        delayQueue.add(delayedDemo);
        delayQueue.add(delayedDemo2);
        delayQueue.remove();
        delayQueue.put(delayedDemo2);
        delayQueue.poll();
        delayQueue.take();
    }
}

class DelayedDemo implements Delayed {

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
