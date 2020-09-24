package com.mashibing.concurrent.yxxy.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用reentrantlock可以进行“尝试锁定”tryLock，这样无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * 
 * @author mashibing
 *  
分析：

t1线程牢牢的拿到锁之后，一直sleep不会释放，如果t2线程中的run方法使用lock.lock(),那么t2线程就会一直傻傻的等着这把锁，不能被其他线程打断；

而使用lockInterruptibly()方法是可以被打断的，主线程main调用t2.interrupt()来打断t2，告诉他是不会拿到这把锁的，别等了；

报错是因为lock.unlock()这个方法报错的，因为都没有拿到锁，无法unlock();是代码的问题，应该判断有锁，已经锁定的情况下才lock.unlock();
 */
public class ReentrantLock3 {
    Lock lock = new ReentrantLock();

    void m1() {
	try {
	    lock.lock();
	    for (int i = 0; i < 4; i++) {
		TimeUnit.SECONDS.sleep(1);

		System.out.println(i);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    lock.unlock();
	    System.out.println("m1 ... finnaly");
	}
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行 可以根据tryLock的返回值来判定是否锁定
     * 也可以指定tryLock的时间，由于tryLock(time)抛出异常，所以要注意unclock的处理，必须放到finally中
     */
    void m2() {
	
	/*boolean locked = lock.tryLock();
	System.out.println("m2 ..." + locked);
	if(locked) lock.unlock();*/
	

	boolean locked = false;
	
	try {
	    locked = lock.tryLock(2, TimeUnit.SECONDS);
	    System.out.println("m2 ..." + locked);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    if (locked)
		lock.unlock();
	    System.out.println("m2 ... finnaly");
	}

    }

    public static void main(String[] args) {
	ReentrantLock3 rl = new ReentrantLock3();
	new Thread(rl::m1).start();
	
	try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
	
	new Thread(rl::m2).start();
    }
}
