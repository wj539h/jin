package com.threadlocal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

public class PhantomRef {
	private static final List<Object> LIST = new LinkedList<>();
	private static final ReferenceQueue<M> Q = new ReferenceQueue<>();
	
	public static void main(String[] args) {
		
		PhantomReference<M> pr = new PhantomReference<>(new M(PhantomRef.class.getName()), Q);
		
		new Thread(() -> {
			while(true) {
				LIST.add(new byte[1024*1024]);
				try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();Thread.currentThread().interrupt();}
				
				System.out.println(pr.get());
			}
		}).start();;
		
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					while(true) {
						Reference<? extends M> poll = Q.poll();
						if(poll != null) {
							System.out.println("pr ref has been recycled : " + poll);
						}
					}
				}
			}
		).start();
		
		try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
	}
}
