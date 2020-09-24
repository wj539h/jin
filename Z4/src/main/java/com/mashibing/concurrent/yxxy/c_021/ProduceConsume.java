package com.mashibing.concurrent.yxxy.c_021;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class ProduceConsume<E> {
    
    LinkedList<E> list = new LinkedList<>();
    final int MAX = 3;
    
    synchronized public void produce(E e) {
	while(true) {
	    if(list.size() == MAX) {
		this.notifyAll();
		try {this.wait();} catch (InterruptedException e1) {e1.printStackTrace();}
	    } else {
		try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e2) {e2.printStackTrace();}
		System.out.println(e+" : produce -- list size : "+list.size());
		list.add(e);
	    }
	}
    }

    synchronized public void consume() {
	while(true) {
	    if(list.isEmpty()) {
		this.notifyAll();
		try {this.wait();} catch (InterruptedException e1) {e1.printStackTrace();}
	    } else {
		try {TimeUnit.MILLISECONDS.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println(list.removeFirst()+" -- consume list size : "+list.size());
	    }
	}
    }
    
    public static void main(String[] args) {
	ProduceConsume<String> pc = new ProduceConsume<String>();
	for(int i=0;i<5;i++) {
	    new Thread(()-> {
        	pc.produce(Thread.currentThread().getName());
	    }).start();
	}
	
	for(int i=0;i<2;i++) {
	    new Thread(()-> {
		pc.consume();
	    }).start();;
	}
    }
}
