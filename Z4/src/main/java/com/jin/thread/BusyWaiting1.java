package com.jin.thread;

import java.util.concurrent.TimeUnit;

class MyObject1 implements Runnable {
    private Monitor1 monitor;

    public MyObject1(Monitor1 monitor) {
	this.monitor = monitor;
    }

    public void gotMsg() {
	monitor.sign = true;
	synchronized (this) {
	    notify();
	}
    }

    @Override
    public void run() {
	synchronized (this) {
	    while (monitor.sign == false) {
		try {
		    wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    System.out.println("He has gone.");
	}
    }

}

class Monitor1 implements Runnable {
    public MyObject1 myObj1 = null;
    public boolean sign = false;

    public void setMyObj1(MyObject1 myObj1) {
	this.myObj1 = myObj1;
    }

    @Override
    public void run() {
	try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
	
	System.out.println("i'm going.");
	myObj1.gotMsg();
    }

}

public class BusyWaiting1 {
    public static void main(String[] args) {
	Monitor1 monitor = new Monitor1();
	MyObject1 o = new MyObject1(monitor);
	monitor.setMyObj1(o);
	new Thread(o).start();
	new Thread(monitor).start();
    }
}
