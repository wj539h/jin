package com.jin.thread;

public class ThreadJoin {
	public static void main(String[] args) throws InterruptedException {
		final Object o = new Object();

		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (o) {
					try {
						System.out.println("me first");// 这个线程先执行，是期望的结果，但是不能保证
						Thread.sleep(3000);
						o.wait();
						System.out.println("wake up and done");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (o) {
					try {
						System.out.println("it's my time");// 如果这个线程先执行，则另一个线程将永远waiting
						Thread.sleep(3000);
						o.notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}
}
