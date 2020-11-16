package com.jin.thread.juc;

import java.util.concurrent.locks.ReentrantLock;
import static com.jin.Const.*;

public class IntLock implements Runnable {
	public static ReentrantLock lock1 = new ReentrantLock();
	public static ReentrantLock lock2 = new ReentrantLock();
	int lock;
	public IntLock(int lock) {
		this.lock = lock;
	}
	public void run() {
		try {
			if (lock == 1) {
				lock1.lockInterruptibly(); 
				sleepThrow(5000);
				lock2.lockInterruptibly();
				pln(tName() + "，end！");
			} else {
				lock2.lockInterruptibly();
				sleepThrow(3000);
				lock1.lockInterruptibly();
				pln(tName() + "，end！");
			}
		} catch (InterruptedException e) {
			pln(tName() + "is interrupted !!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} finally {
			if (lock1.isHeldByCurrentThread()) {
				pln("lock1 unlock : "+tName());
				lock1.unlock();
			}
			if (lock2.isHeldByCurrentThread()) {
				pln("lock2 unlock : "+tName());
				lock2.unlock();
			}
			pln(tName() + "，exit。");
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new IntLock(1), "t1");
		Thread t2 = new Thread(new IntLock(2), "t2");
		t1.start();
		sleepThrow(1);
		t2.start();
		sleepThrow(2000);
		t2.interrupt(); 
	}
}
