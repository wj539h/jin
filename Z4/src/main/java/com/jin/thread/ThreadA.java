package com.jin.thread;
public class ThreadA {
	public static void main(String[] args) {
		ThreadB b = new ThreadB();// ThreadB status: new
		b.start();// ThreadB status: runnable
		
		synchronized (b) {
			try {
				System.out.println("等待对象b完成计算。。。");
				Thread.sleep(1000);
				b.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("b对象计算的总和是：" + b.total);
		}
	}
}

class ThreadB extends Thread {
	int total;
	@Override
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 101; i++) {
				total += i;
			}
			notifyAll();
		}
	}
}
