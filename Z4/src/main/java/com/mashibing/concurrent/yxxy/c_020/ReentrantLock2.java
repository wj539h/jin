package com.mashibing.concurrent.yxxy.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * reentrantlock用于替代synchronized
 * 使用reentrantlock可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 * @author mashibing
 */
public class ReentrantLock2 {
    Lock lock = new ReentrantLock();

    void m1() {
	lock.lock(); //synchronized(this)
	try {
	    for (int i = 0; i < 4; i++) {
		TimeUnit.SECONDS.sleep(1);

		System.out.println(i);
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} finally {
	    lock.unlock();
	}
    }

    void m2() {
	lock.lock();
	System.out.println("m2 ...");
	lock.unlock();
    }

    public static void main(String[] args) {
	ReentrantLock2 rl = new ReentrantLock2();
	new Thread(rl::m1).start();
	
	try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
	
	new Thread(rl::m2).start();
    }
}
