package com.mashibing.concurrent.yxxy.c_004;

/**
 * synchronized关键字 对某个对象加锁
 * 
 * @author mashibing
 */

public class T {

    private static int count = 10;

    public synchronized static void m() { //这里等同于synchronized(yxxy.c_004.T.class)
	count--;
	System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    //相当于是下面这种写法
    public static void mm() {
	synchronized (T.class) { //考虑一下这里写synchronized(this)是否可以？
	    //t.class 是Class里面的对象 [反射知识]
	    count--;
	}
    }

}