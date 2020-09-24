package com.mashibing.concurrent.yxxy.c_007;

public class T {

    public synchronized void m1() {
	System.out.println(Thread.currentThread().getName() + " m1 start...");
	
	try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
	
	System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2() {
	try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
	System.out.println(Thread.currentThread().getName() + " m2 ");
    }

    public static void main(String[] args) {
	/*T t = new T();
	
	new Thread(() -> t.m1(), "t1").start();
	new Thread(() -> t.m2(), "t2").start();*/
	
	T t = new T();
	new Thread(t::m1, "t1").start();
	new Thread(t::m2, "t2").start();
    }

}






/*
t1 m1 start...
t2 m2
t1 m1 end
分析：

t1线程执行m1方法，开始睡10s，在这过程之中，t2线程执行m2方法，5s之后打印了m2；由此可见在m1执行的过程之中，m2是可以运行的。
同步方法的执行过程中，非同步方法是可以执行的。只有synchronized这样的方法在运行时候才需要申请那把锁，而别的方法是不需要申请那把锁的。
new Thread(()->t.m1())这个写法是java8里面的Lambda表达式，一种简写，还可以写成这样：*/