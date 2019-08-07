package com.yangyun.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName DeadLockDemo
 * @Description: 死锁指两个或两个以上的进程在执行过程中, 因争夺资源而造成的一种互相等待的现象
 * @Author 86155
 * @Date 2019/8/7 21:42
 * @Version 1.0
 **/
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new LockData(lockA, lockB), "ThreadAAAA").start();
        new Thread(new LockData(lockB, lockA), "ThreadBBBB").start();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class LockData implements Runnable {
    private String lockA;
    private String lockB;

    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有的锁: " + lockA + ", 尝试获取锁: " + lockB);
            try {
                TimeUnit.SECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有的锁: " + lockB + ", 尝试获取锁: " + lockA);
            }
        }
    }
}
