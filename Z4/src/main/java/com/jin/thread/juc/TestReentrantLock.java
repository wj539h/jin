package com.jin.thread.juc;

import java.util.concurrent.locks.ReentrantLock;
import static com.jin.Const.*;
public class TestReentrantLock {
    ReentrantLock rtLock = new ReentrantLock(true);
    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        Person p = new Person();
        t1 = new Thread(p, "t1");t1.start();
        t2 = new Thread(p, "t2");t2.start();
        t3 = new Thread(p, "t3");t3.start();
    }

    public static class Person implements Runnable {
        private int age = 0;
        ReentrantLock rtLock = new ReentrantLock(true);
        public void run() {
            while(true) {
                addAge();
            }
        }
        private void addAge() {
            try {
                //rtLock.lock();
                rtLock.lockInterruptibly();
                age++;
                p("addAge");
            } catch (Exception e) {
                p("catch");
                e.printStackTrace();
            } finally {
                boolean b = rtLock.getHoldCount() == 6;
                while(b) {
                    p("finally");
                    rtLock.unlock();
                    if(rtLock.getHoldCount() == 2)
                        t2.interrupt();
                    if(rtLock.getHoldCount() == 0)
                        break;
                }
            }
        }
        private void p(String mName) {
            pln(mName+" "+tName()+" --- "+age+"HoldCount : "+rtLock.getHoldCount()+"    QueueLength : "+rtLock.getQueueLength());
        }
        private void p() {
            p("");
        }
    }
}
