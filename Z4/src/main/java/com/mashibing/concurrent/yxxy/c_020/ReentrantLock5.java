package com.mashibing.concurrent.yxxy.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * ReentrantLock还可以指定为公平锁
 * 
 * @author mashibing
 * 
 * 公平锁：等待时间长的线程先执行

竞争锁：多个线程一起竞争一个锁

竞争锁相对效率高
 */

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock5 extends Thread {
        
    private static Lock lock = new ReentrantLock(true); //参数为true表示为公平锁，请对比输出结果
    
    public void run() {
        for(int i=0; i<200; i++) {
            lock.lock();
            try{
        	try {TimeUnit.MILLISECONDS.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName()+"获得锁");
            }finally{
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        ReentrantLock5 rl=new ReentrantLock5();
        Thread th1=new Thread(rl);
        Thread th2=new Thread(rl);
        th1.start();
        th2.start();
    }
}