package com.mashibing.concurrent.yxxy.c_006;

/**
 * 对比上面一个小程序，分析一下这个程序的输出
 * @author mashibing
 */
 
 
public class T implements Runnable {
 
    private int count = 10;
     
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
     
    public static void main(String[] args) {
         
        for(int i=0; i<5; i++) {
            T t = new T();
            new Thread(t, "THREAD" + i).start();
        }
    }
     
}






/**
THREAD0 count = 9
THREAD4 count = 9
THREAD3 count = 9
THREAD1 count = 9
THREAD2 count = 9
分析：

启动了5个线程，因为每次都是new了一个t，每个线程都能锁住t，一共有5个t，5个count；所以这里5个线程执行完，count都是9；
但是因为不知道哪个线程先被cpu执行，所以thread名字的顺序是随机的；
 */