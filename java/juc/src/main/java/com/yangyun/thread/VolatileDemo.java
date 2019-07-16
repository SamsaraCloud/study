package com.yangyun.thread;

/**
 * @description 验证 volatile 可见性
 * @author yangyun
 * @date 2019/5/23
 * @return
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j= 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        // Thread.activeCount() 表示运行中的线程数, 默认有 gc 线程, main 线程
        while(Thread.activeCount() > 2){
            // 当运行线程数大于 2 表示上面线程计算还没结束, 需要线程让出 cpu 资源
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + ":\t" + myData.number);
    }

    // 验证 volatile 可见性
    public static void seeOkByVolatile() {
        // 当 number 没有volatile 修饰时
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "updated number to 60");
        }, "AAA"
        ).start();

        while (myData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + "\t misson is over");
    }
}

class MyData {
    volatile int number = 0; // 当number被修改后, 其他线程能及时知道number的值被修改
//    int number = 0;

    public void  addTo60 (){
        this.number = 60;
    }

    // 在有 volatile 修饰的时候, 验证 volatile 是否保证原子性(根据运行结果, volatile 不保证原子性)
    public void addPlusPlus (){
        number++;
    }
}
