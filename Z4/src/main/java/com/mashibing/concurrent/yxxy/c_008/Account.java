package com.mashibing.concurrent.yxxy.c_008;

import java.util.concurrent.TimeUnit;

public class Account {
    String name;
    double balance;
     
    public synchronized void set(String name, double balance) {
        this.name = name;
         
        try {Thread.sleep(1500);} catch (InterruptedException e) {e.printStackTrace();}
         
        this.balance = balance;
    }
     
    public synchronized double getBalance(String name) {
        return this.balance;
    }
     
     
    public static void main(String[] args) {
        Account a = new Account();
        new Thread(()->a.set("zhangsan", 100.0)).start();
         
        try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
         
        System.out.println(a.getBalance("zhangsan"));
         
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
         
        System.out.println(a.getBalance("zhangsan"));
    }
}





/*
0.0
100.0
　　

分析：

主线程里面第一次读zhangsan里面的钱是0.0，第二次读是100.0；原因是set修改钱的时候过程中，sleep了2s钟；为什么sleep 2s就是放大了在线程的执行过程之中的时间差，set钱方法里面this.name=name和this.balance=balance之间可能是会被别的程序执行的；
在线程的执行过程set钱之中，尽管写的这个方法set加上了synchronized锁定了这个对象，锁定这个对象过程之中，它仍然有可能被那些非锁定的方法/非同步方法访问的；
尽管对写进行了加锁，但是由于没有对读加锁，那么有可能会读到在写的过程中还没有完成的数据，产生了脏读问题；*/