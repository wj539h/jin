package com.mashibing.concurrent.yxxy.c_013;

/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，也就是说volatile不能替代synchronized
 * 运行下面的程序，并分析结果
 * @author mashibing
 */

import java.util.ArrayList;
import java.util.List;

public class T {
    
    volatile int count = 0;

    void m() {
	for (int i = 0; i < 10000; i++)
	    count++;
    }

    public static void main(String[] args) {
        T t = new T();
         
        List<Thread> threads = new ArrayList<Thread>();
         
        for(int i=0; i<10; i++) {
            threads.add(new Thread(t::m, "thread-"+i));
        }
         
        threads.forEach((o)->o.start());
         
	threads.forEach((o)->{
	    try {o.join();} catch (InterruptedException e) {e.printStackTrace();}
	});
         
        System.out.println(t.count);
         
    }

}
/*
volatile和synchronized区别？
首先线程2可能读到自己缓冲区里面的count是99或者更小的数，此时线程3将count修改成100
线程1先去读count，读进来后通知其他线程，比如线程2的缓冲区失效(让99失效)，线程2重新去读，
此时线程1的缓冲区100已经读进来了，+1后还没有往主内存写呢，线程2去读最新的100.

加了volatile只能保证其他线程读到最新的，但是线程1在往回写的过程中线程2已经把最新的100读过来了
此时线程1往回写成101，然后线程2也+1后往回写，由于线程2之前读到的是100，所以又写了一次101
   
volatile只保证可见性，并不保证原子性； 说白了就是写回去的时候不检查
synchronized既保证可见性，又保证原子性；但效率要比volatile低不少；
如果只需要保证可见性的时候，使用volatile，不要使用synchronized；
 
Thread.join()方法简单解释：插队，等待该线程完成后执行该线程
*/