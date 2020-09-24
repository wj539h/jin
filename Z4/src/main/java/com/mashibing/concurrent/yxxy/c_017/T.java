package com.mashibing.concurrent.yxxy.c_017;

/**
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成另外一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外的对象
 * @author mashibing
 */
 
import java.util.concurrent.TimeUnit;
 
public class T {
    Object o = new Object();
 
    void m() {
        synchronized(o) {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }
     
    public static void main(String[] args) {
        T t = new T();
        //启动第一个线程
        new Thread(t::m, "t1").start();
         
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //创建第二个线程
        Thread t2 = new Thread(t::m, "t2");
         
        t.o = new Object(); //锁对象发生改变，所以t2线程得以执行，如果注释掉这句话，线程2将永远得不到执行机会
         
        t2.start();
    }
}

/*
分析：

t.o = new Object();锁的对象发生改变，就不需要锁原来的对象，直接锁新对象就行了；而新对象还没有锁的，所以t2线程就被执行了；
所以，这就证明这个锁是锁在什么地方？是锁在堆内存里new出来的对象上，不是锁在栈内存里头o的引用，不是锁的引用，而是锁new出来的真正的对象；
锁的信息是记录在堆内存里的。
*/