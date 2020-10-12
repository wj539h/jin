package com.jin.ds;

import java.util.concurrent.TimeUnit;

public abstract class TimerCallBack {
	public abstract void doSomething();

	public static void timerPrint(TimeUnit unit, TimerCallBack tcb) {
		Long start = System.nanoTime();
		tcb.doSomething();
		Long end = System.nanoTime();
		long diff = end - start;
		System.out.println(unit.convert(diff, TimeUnit.NANOSECONDS));
	}

	public static void timerPrint(TimerCallBack tcb) {
		Long start = System.nanoTime();
		tcb.doSomething();
		Long end = System.nanoTime();
		long diff = end - start;
		System.out.println(diff);
	}
}
