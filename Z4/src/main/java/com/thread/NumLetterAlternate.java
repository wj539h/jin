package com.thread;


public class NumLetterAlternate implements Runnable {
	char i = 0;
	int sign = 0;

	public void printNum() {
		this.notify();
		try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		if(i>90) {
			try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
		System.out.println(Thread.currentThread().getName()+"---"+(int)i);
		i+=96;
		sign++;
		if(sign%2==0) {
			i++;
		}
	}

	public void printLetter() {
		this.notify();
		try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		if(i<90) {
			try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
		}
		System.out.println(Thread.currentThread().getName()+"---"+i);
		i-=96;
		sign++;
		if(sign%2==0) {
			i++;
		}
	}

	@Override
	public void run() {
		while(true) {
			synchronized (this) {
				if(i>90) {
					printLetter();
				} else if(i>26) {
					break;
				}else {
					printNum();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		NumLetterAlternate nla=new NumLetterAlternate();
    	nla.i=1;
    	new Thread(nla).start();
    	new Thread(nla).start();
	}
}

