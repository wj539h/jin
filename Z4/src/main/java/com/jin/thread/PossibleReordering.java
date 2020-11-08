package com.jin.thread;

public class PossibleReordering {
	static int x = 0, y = 0, a = 0, b = 0;

	static void init() {
		x = 0;y = 0;a = 0;b = 0;
	}

	public static void main(String[] args) throws InterruptedException {
		int i = 0;
		for (;;) {
			init();
			Thread one = new Thread(new Runnable() {
				public void run() {
					a = 1;
					x = b;
				}
			});

			Thread other = new Thread(new Runnable() {
				public void run() {
					b = 1;
					y = a;
				}
			});
			one.start();other.start();
			one.join();other.join();
			System.out.println("(" + x + "," + y + ") --- " + i++);
			if(x==0 && y==0) {
				System.out.println(i);
				break;
			}
		}
	}
}
