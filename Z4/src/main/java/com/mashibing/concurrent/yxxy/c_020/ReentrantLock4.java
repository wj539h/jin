package com.mashibing.concurrent.yxxy.c_020;

/**
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 *  分析：

t1线程牢牢的拿到锁之后，一直sleep不会释放，如果t2线程中的run方法使用lock.lock(),那么t2线程就会一直傻傻的等着这把锁，不能被其他线程打断；

而使用lockInterruptibly()方法是可以被打断的，主线程main调用t2.interrupt()来打断t2，告诉他是不会拿到这把锁的，别等了；

报错是因为lock.unlock()这个方法报错的，因为都没有拿到锁，无法unlock();是代码的问题，应该判断有锁，已经锁定的情况下才lock.unlock();
 * @author mashibing
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock4 {

    public static void main(String[] args) {
	Lock lock = new ReentrantLock();

	Thread t1 = new Thread(() -> {
	    try {
		lock.lock();
		System.out.println("t1 start");
		TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
		System.out.println("t1 end");
	    } catch (InterruptedException e) {
		System.out.println("interrupted! T1");
	    } finally {
		lock.unlock();
		System.out.println("T1 finnally, to unlock");
	    }
	});
	t1.start();

	Thread t2 = new Thread(() -> {
	    try {
		//lock.lock();
		lock.lockInterruptibly(); //可以对interrupt()方法做出响应
		System.out.println("t2 start");
	    } catch (InterruptedException e) {
		System.out.println("interrupted! T2");
		t1.interrupt();
	    } finally {
		if(lock.tryLock())
		    lock.unlock();
	    }
	});
	t2.start();

	try {
	    TimeUnit.SECONDS.sleep(1);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	t2.interrupt(); //打断线程2的等待
    }
}