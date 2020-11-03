package com.jin.threadlocal;

import java.lang.ref.SoftReference;

public class SoftRef {
	public static void main(String[] args) {
		/*SoftReference<byte[]> m = new SoftReference<byte[]>(new byte[1024*1024*10]);
		System.out.println(m.get());
		System.gc();
		
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		
		System.out.println(m.get());
		
		byte[] b = new byte[1024*1024*15];
		System.out.println(m.get());*/
		SoftReference<byte[]> m = new SoftReference<byte[]>(new byte[1024*1024*10]);
		byte[] b = new byte[1024*1024*15];
		System.out.println(m.get());
	}
}
