package com.thread;

public class TestProducerConsumer {
	public static void main(String[] agrs) {
		SyncStack ss = new SyncStack();
		Producer p = new Producer(ss);
		ConSumer c = new ConSumer(ss);
		new Thread(p).start();
		new Thread(c).start();

	}
}

class Woto {

	int id;

	public Woto(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Woto:" + id;
	}
}

class SyncStack {
	int index = 0;
	Woto[] w = new Woto[8];

	public synchronized void push(Woto wt) {
		while (index == w.length) {
			try {
				this.wait();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		this.notify();
		w[index] = wt;
		index++;
	}

	public synchronized Woto pop() {
		while (index == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notify();
		index--;
		return w[index];
	}
}

class Producer implements Runnable {
	SyncStack ss = null;

	public Producer(SyncStack ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			Woto wt = new Woto(i);
			ss.push(wt);
			System.out.println("生产了=" + wt);
			try {
				Thread.sleep((int) (Math.random() * 200));
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
}

class ConSumer implements Runnable {
	SyncStack ss = null;

	public ConSumer(SyncStack ss) {
		this.ss = ss;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {

			Woto wt = ss.pop();
			System.out.println("消费了=" + wt);
			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}