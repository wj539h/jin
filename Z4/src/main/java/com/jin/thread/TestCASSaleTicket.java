package com.jin.thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jin.Const.*;

public class TestCASSaleTicket {
	public static void main(String args[]) {
		//SaleTicketA st = new SaleTicketA();
		SaleTicket st = new SaleTicket();
		String t1Name="t1",t2Name="t2";
		Thread t1 = new Thread(st,t1Name);t1.start();
		Thread t2 = new Thread(st,t2Name);t2.start();
			
		tJoin(t1);tJoin(t2);
		int o = map.get(t1Name).size()+map.get(t2Name).size();
		pln(map.get(t1Name).size()+" ----- "+map.get(t2Name).size()+" : "+o);
	}
	static volatile public int ticket = 200000;
	static Map<String, List<String>> map = new HashMap<String, List<String>>();
	static class SaleTicketA implements Runnable {
		public AtomicInteger ticketA = new AtomicInteger(ticket);
		public void run() {
			while (true) {
				if (ticketA.intValue() <= 0) break;
				pln(tName()+" sale : "+ ticketA.getAndDecrement());
				synchronized (this) {//这块是要保证往集合里添加的时候不造成线程问题
					List l = map.get(tName()) == null?new ArrayList(ticketA.intValue()):map.get(tName());
					l.add(ticketA.intValue());
					map.put(tName(), l);
				}
				//sleep(100);
			}
		}
	}
	static class SaleTicket implements Runnable {
		public void run() {
			while (true) {
				//synchronized (this) {
				if (ticket <= 0) break;
				pln(tName()+" sale : "+ticket--);
				synchronized (this) {
					List l = map.get(tName()) == null?new ArrayList(ticket):map.get(tName());
					l.add(ticket);
					map.put(tName(), l);
				}
				//sleep(100);
				//}
			}
		}
	}
}

