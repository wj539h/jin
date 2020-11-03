package com.jin.thread;

import java.util.concurrent.TimeUnit;

class MyObject implements Runnable {
    private Monitor monitor;

    public MyObject(Monitor monitor) {
	this.monitor = monitor;
    }

    @Override
    public void run() {
	try {
	    TimeUnit.SECONDS.sleep(3);
	    System.out.println("i'm going.");
	    monitor.gotMessage();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
}

class Monitor implements Runnable {
    private volatile boolean go = false;//这里十分重要

    public void gotMessage() throws InterruptedException {
	go = true;
    }

    public void watching() {
	while (go == false);
	System.out.println("He has gone.");
    }

    @Override
    public void run() {
	watching();
    }
}

public class BusyWaiting {
    public static void main(String[] args) {
	Monitor monitor = new Monitor();
	MyObject o = new MyObject(monitor);
	new Thread(o).start();
	new Thread(monitor).start();
    }
}