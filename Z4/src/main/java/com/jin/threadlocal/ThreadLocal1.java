package com.jin.threadlocal;

public class ThreadLocal1 {
	public static void main(String[] args) {
		ThreadLocal1 tl1 = new ThreadLocal1();
		//tl1.notUsingThreadLocal();
		tl1.usingThreadLocal();
	}

	public void notUsingThreadLocal() {
		Person p = new Person();
		new Thread(new Runnable() {
			public void run() {
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				p.setName("lisi");
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				System.out.println(p.getName());
			}
		}).start();
	}
	
	public void usingThreadLocal() {
		Person p = new Person();
		ThreadLocal<Person> tlp = new ThreadLocal<Person>();
		new Thread(new Runnable() {
			public void run() {
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
				tlp.set(p);
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
				System.out.println(tlp.get().getName());
			}
		}).start();
	}
}



class Person {
	private String name = "zhangsan";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}