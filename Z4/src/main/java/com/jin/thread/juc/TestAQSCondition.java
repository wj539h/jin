package com.jin.thread.juc;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import static com.jin.Const.*;
public class TestAQSCondition {
    static ReentrantLock rtLock = null;
    static Condition cond = null;

    public static void main(String[] args) {
        /*SampleCond sc = new SampleCond();
        sc.runSampleCond();*/
        PC pc =new PC();
        pc.runPC();
    }

    public static class SampleCond implements Runnable {
        public void run() {
            if (tName().equals("t1") || tName().equals("t2") ||
                    tName().equals("t3") || tName().equals("t4")) {
                doWait();
            } else {
                doSignal();
            }
        }
        public void runSampleCond() {
            rtLock = new ReentrantLock();
            cond = rtLock.newCondition();
            Thread t1 = new Thread(this, "t1");t1.start();sleep(1);
            Thread t2 = new Thread(this, "t2");t2.start();sleep(1);
            Thread t3 = new Thread(this, "t3");t3.start();sleep(1);
            Thread t4 = new Thread(this, "t4");t4.start();sleep(1);
            Thread t5 = new Thread(this, "t5");t5.start();
        }
        private void doWait() {
            rtLock.lock();
            try {
                pln(tName()+" : begin wait");
                cond.await();
                pln(tName()+" : end wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                rtLock.unlock();//(5)
            }
        }

        private void doSignal() {
            rtLock.lock();
            try {
                pln(tName()+" : begin signal");
                cond.signalAll();
                pln(tName()+" : end signal");
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                rtLock.unlock();//(5)
            }
        }
    }
    /*----------------------------------------------------------------------------------------*/
    public static class PC implements Runnable {
        AtomicInteger i = new AtomicInteger(0);
        @Override
        public void run() {
            rtLock.lock();
            try {
                while(true) {
                    if(tName().equals("p")) {
                        if(i.get() == 3) {
                            cond.signal();
                            cond.await();
                        } else
                            pln(tName() +" : "+i.incrementAndGet());
                    } else if(tName().equals("c")) {
                        if(i.get() == 0) {
                            cond.signal();
                            cond.await();
                        } else
                            pln(tName() +" : "+i.decrementAndGet());
                    }
                    sleep500();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void runPC() {
            rtLock = new ReentrantLock();
            cond = rtLock.newCondition();
            Thread c = new Thread(this, "c");c.start();sleep(1);
            Thread p = new Thread(this, "p");p.start();
        }
    }
}


