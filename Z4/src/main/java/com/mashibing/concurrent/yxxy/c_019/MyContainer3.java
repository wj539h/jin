package com.mashibing.concurrent.yxxy.c_019;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 *
 * 分析下面这个程序，能完成这个功能吗？
 * @author mashibing
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MyContainer3 {
    List lists = new ArrayList();

    public void add(Object o) {
	lists.add(o);
    }
    
    public int size() {
	return lists.size();
    }

    public static void main(String[] args) {
	MyContainer3 c = new MyContainer3();
	CountDownLatch latch = new CountDownLatch(1);
	new Thread(() -> {
	    for (int i = 0; i < 4; i++) {
		if (i == 2) {
		    latch.countDown();
		}
		c.add(new Object());
		System.out.println("add " + i);
		try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
	    }
	}, "t1").start();
	
	new Thread(() -> {
	    if(c.size() != 2) {
		try {latch.await();} catch (InterruptedException e) {e.printStackTrace();}
	    }
	    System.out.println("t2 结束");
	}, "t2").start();
	
    }
}

/*
使用门闩
CountDownLatch(1)，CountDown往下数，当1变为0的时候门闩就开了，latch.countDown()调用一次数就往下-1；
latch.await()，门闩的等待是不需要锁定任何对象的；
*/
