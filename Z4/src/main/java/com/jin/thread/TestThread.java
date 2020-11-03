package com.jin.thread;



public class TestThread  {
	public static void main(String args[]) {
		Ticket t = new Ticket(0);
		for(int i=0;i<2;i++) {
			new Thread(t).start();
		}
		//try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		/*new Thread(new P(t)).start();
		new Thread(new C(t)).start();*/
		
	}
}




class Ticket implements Runnable {

	public Integer ticket;
	public Ticket(Integer ticket) {
		this.ticket = ticket;
	}
	public ThreadLocal<Integer> tlTicket;
	
	@Override
	public void run() {
		/*while(true) {
			synchronized(this){
				if (ticket <= 0)
					break;
				System.out.println(Thread.currentThread().getName()+" is selling " + ticket);
				try {Thread.sleep(100L);} catch (InterruptedException e) {e.printStackTrace();}
				ticket--;
			}
		}*/
		/*while(true) {
			if(ticket <= 0) {
				return;
			}
			buyTicket();
		}*/
		count();
	}
	
	private void count() {
		for(int i=0;i<10;i++) {
			System.out.println(Thread.currentThread().getName()+" : " + ticket++);
			try {Thread.sleep(100L);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	private void countTl() {
		if(tlTicket == null) {
			tlTicket = new ThreadLocal<Integer>();
		}
		tlTicket.set(ticket);
		for(int i=0;i<10;i++) {
			Integer temp = tlTicket.get();
			System.out.println(Thread.currentThread().getName()+" : " + temp++);
			tlTicket.set(temp);
		}
		//System.out.println(Thread.currentThread().getName()+" : " + tlTicket.get());
	}
	
	synchronized private void buyTicket() {
		System.out.println(Thread.currentThread().getName()+" is buying " + ticket--);
		try {Thread.sleep(50);} catch (InterruptedException e) {}
	}
}

class P implements Runnable {
	Ticket t;
	P(Ticket t) {this.t=t;}
	public void run() {
		while(true) {
			synchronized (t) {
				if(t.ticket > 3) {
					t.notify();
					try {t.wait();} catch (InterruptedException e) {e.printStackTrace();}
				}
				System.out.println(Thread.currentThread().getName()+" : " + t.ticket++);
				try {Thread.sleep(500L);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}
class C implements Runnable {
	Ticket t;
	C(Ticket t) {this.t=t;}
	public void run() {
		while(true) {
			synchronized (t) {
				if(t.ticket <= 0) {
					t.notify();
					try {t.wait();} catch (InterruptedException e) {e.printStackTrace();}
				}
				System.out.println(Thread.currentThread().getName()+" : " + t.ticket--);
				try {Thread.sleep(500L);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}

