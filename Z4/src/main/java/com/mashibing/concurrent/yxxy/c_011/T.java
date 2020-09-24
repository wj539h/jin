package com.mashibing.concurrent.yxxy.c_011;

/**
 * 程序在执行过程中，如果出现异常，默认情况锁会被释放
 * 所以，在并发处理的过程中，有异常要多加小心，不然可能会发生不一致的情况。
 * 比如，在一个web app处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据。
 * 因此要非常小心的处理同步业务逻辑中的异常
 * @author mashibing
 */
 
import java.util.concurrent.TimeUnit;
 
public class T {
    int count = 0;
    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + " start");
        while(true) {
            count ++;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
            
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
             
            if(count == 2) {
                int i = 1/0; //此处抛出异常，锁将被释放，要想不被释放，可以在这里进行catch，然后让循环继续
		/*try {
		    int i = 1/0;
		} catch (Exception e) {
		    e.printStackTrace();
		}*/
            }
        }
    }
     
    public static void main(String[] args) {
        T t = new T();
         
        new Thread(()->t.m(), "t1").start();
         
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
         
        new Thread(()->t.m(), "t2").start();
    }
     
}


/*执行结果

t1 start
t1 count = 1
t1 count = 2
t1 count = 3
t1 count = 4
t1 count = 5
t2 start
t2 count = 6
Exception in thread "t1" java.lang.ArithmeticException: / by zero
    at yxxy.c_011.T.m(T.java:28)
    at yxxy.c_011.T.lambda$0(T.java:36)
    at java.lang.Thread.run(Thread.java:745)
t2 count = 7
t2 count = 8
t2 count = 9　
分析：
t1线程启动后，如果int i=1/0这里抛了异常后，锁不被释放的话，t2线程就永远启动不起来，永远执行不了；
但是抛出异常之后，锁被释放了，t2得到了执行；
 
解决：
处理异常，锁不被释放，循环继续，t2线程永远执行不了：*/