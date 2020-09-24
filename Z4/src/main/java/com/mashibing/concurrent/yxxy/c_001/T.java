package com.mashibing.concurrent.yxxy.c_001;

import org.openjdk.jol.info.ClassLayout;

/**
 * synchronized关键字 对某个对象加锁
 * 
 * @author mashibing
 */

public class T {

    private int count = 10;
    private Object o = new Object();

    public void m() {
	synchronized (o) { //任何线程要执行下面的代码，必须先拿到o的锁
	    count--;
	    System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
    }
    public static void main(String[] args) {
	Object object = new Object();
	System.out.println("hash: " + object.hashCode());
	System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }
}