package com.jin.thread;

public class TestProducerConsumer {
	public static void main(String[] agrs) {
		/*SyncStack ss = new SyncStack();
		Producer p = new Producer(ss);
		ConSumer c = new ConSumer(ss);
		new Thread(p).start();
		new Thread(c).start();*/
		
		PCSingleClass t = new PCSingleClass();
		new Thread(t).start();
		new Thread(t).start();
	}
}

class PCSingleClass implements Runnable {
	char p_c = 'p';
	int i = 0;
	public void run() {
		while (true) {
			synchronized (this) {
				if (i == 0 || p_c == 'p') {
					p();
				}
				if (i == 3 || p_c == 'c') {
					c();
				}
			}
		}
	}
	public void p() {
		System.out.println(Thread.currentThread().getName()+" 生产了 : "+(++i));
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		if(i==3) {
			p_c = 'c';
			this.notify();
			try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	public void c() {
		System.out.println(Thread.currentThread().getName()+" 消费了 : "+i--);
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		if(i==0) {
			p_c = 'p';
			this.notify();
			try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
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