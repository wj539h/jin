package com.jin.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class TicketDemo implements  Runnable{
    private static volatile AtomicInteger ticketSum = new AtomicInteger(250);
    private static int finalTotal = 0;

    @Override
    public void run() {
        int count;
        while ((count = ticketSum.decrementAndGet()) >= 0) {

            System.out.println(Thread.currentThread().getName() + "卖出了第" + ++count + "张票");
            finalTotal++;
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new TicketDemo(), "t1");
        Thread t2 = new Thread(new TicketDemo(), "t2");
        Thread t3 = new Thread(new TicketDemo(), "t3");
        long startTime = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        while (true) {
            if (!t1.isAlive() && !t2.isAlive() && !t3.isAlive()) {
                long endTime = System.currentTimeMillis();
                System.out.println("最终售出：" + finalTotal);
                System.out.println("最终耗时：" + (endTime - startTime) + "毫秒.");
                break;
            }

        }

    }
}
