package com.jin.threadlocal;

public class NormalRef {
	public static void main(String[] args) {
		M m = new M(NormalRef.class.getName());
		m = null;
		try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		System.gc();
	}
}
