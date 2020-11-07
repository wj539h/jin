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
		Thread t = null;
		for(int i=0;i<2;i++) {
			t = new Thread(st);
			t.start();
		}
		tJoin(t);
		int o = map.get("Thread-0").size()+map.get("Thread-1").size();
		pln(map.get("Thread-0").size()+" ----- "+map.get("Thread-1").size()+" : "+o);
	}
	static public int ticket = 200000;
	static class SaleTicketA implements Runnable {
		public AtomicInteger ticketA = new AtomicInteger(ticket);
		public void run() {
			while (true) {
				if (ticketA.intValue() <= 0) break;
				pln(tName()+" sale : "+ ticketA.getAndDecrement());
				List l = map.get(tName()) == null?new ArrayList(ticketA.intValue()):map.get(tName());
				l.add(ticketA.intValue());
				map.put(tName(), l);
				//sleep(100);
			}
		}
	}
	static Map<String, List<String>> map = new HashMap<String, List<String>>();
	static class SaleTicket implements Runnable {
		public void run() {
			while (true) {
				//synchronized (this) {
				if (ticket <= 0) break;
				pln(tName()+" sale : "+ticket--);
				List l = map.get(tName()) == null?new ArrayList(ticket):map.get(tName());
				l.add(ticket);
				map.put(tName(), l);
				//sleep(100);
				//}
			}
		}
	}
}

