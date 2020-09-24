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
import java.util.concurrent.TimeUnit;

public class MyContainer2 {
    List lists = new ArrayList();

    public void add(Object o) {
	lists.add(o);
    }
    
    public int size() {
	return lists.size();
    }

    public static void main(String[] args) {
	MyContainer2 c = new MyContainer2();

	new Thread(() -> {
	    synchronized(c) {
		for (int i = 0; i < 4; i++) {
		    if (i == 2) {
			c.notify();
			try {c.wait();} catch (InterruptedException e) {e.printStackTrace();}
		    }
		    c.add(new Object());
		    System.out.println("add " + i);
		    try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
		}
		
	    }
	}, "t1").start();
	
	new Thread(() -> {
	    synchronized(c) {
		if(c.size() != 2) {//因为就是2个线程，所以用if没关系，后面的生产者消费者的例子就不能用了
		    try {c.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
		System.out.println("t2 结束");
		c.notify();
	    };
	}, "t2").start();
	
    }
}

/*

但是上面代码还存在两个问题：

1）由于没加同步,c.size()等于5的时候，假如另外一个线程又往上增加了1个，实际上这时候已经等于6了才break，所以不是很精确；

2）浪费CPU，t2线程的死循环很浪费cpu
*/
