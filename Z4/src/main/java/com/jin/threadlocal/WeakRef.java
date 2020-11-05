package com.jin.threadlocal;

import java.lang.ref.WeakReference;

public class WeakRef {
	public static void main(String[] args) {
		WeakReference<M> m = new WeakReference<M>(new M(WeakRef.class.getName()));
		System.out.println(m.get());
		System.gc();
		System.out.println(m.get());
		
		ThreadLocal<M> tl = new ThreadLocal<>();
		tl.set(new M(WeakRef.class.getName()));
		tl.remove();
	}
}
