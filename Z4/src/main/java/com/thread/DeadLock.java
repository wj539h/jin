package com.thread;

public class DeadLock {
    public static final Integer forA = new Integer(0);
    public static final Integer forB = new Integer(0);

    public static void main(String[] args) {
	new Thread(/* Runnable ta= */new Runnable() {
	    @Override
	    public void run() {
		saySomething();
	    }

	    public void saySomething() {
		System.out.println("A1");
		synchronized (forA) {
		    try {
			Thread.sleep(200);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		    System.out.println("A2");
		    synchronized (forB) {
			System.out.println("A3");
		    }
		}
	    }
	}).start();

	new Thread(/* Runnable tb= */new Runnable() {
	    @Override
	    public void run() {
		saySomething();
	    }

	    public void saySomething() {
		System.out.println("B1");
		synchronized (forB) {
		    // try {Thread.sleep(200);} catch
		    // (InterruptedException e)
		    // {e.printStackTrace();}
		    System.out.println("B2");
		    synchronized (forA) {
			System.out.println("B3");
		    }
		}
	    }
	}).start();

    }
}
