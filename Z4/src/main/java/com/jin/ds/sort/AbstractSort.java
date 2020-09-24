package com.jin.ds.sort;

import java.util.Arrays;
import java.util.logging.Level;

import com.jin.ds.TimeLogger;

public abstract class AbstractSort {
    protected int a[] = { 9, 3, 4, 1, 6 , 8, 0, 7, 5, 2 };
    protected int len;
    public AbstractSort(int arr[]) {
	this.a = arr;
	len = a.length;
    }
    public void consolePrint() {
	String ori = "original Arr : ";
	String sorted = " sorted  Arr : ";
	print(ori);
	sort();
	print(sorted);
    }
    public void print(String msg) {
	for (int e : a) {
	    msg += e + " ";
	}
	System.out.println(msg);
    }
    
    public void timeLogPrint() {
	TimeLogger tl = new TimeLogger();
	tl.start();
	sort();
	tl.stopAndPrint(Level.INFO);
    }
    
    public void dataCheck() {
	int arr2[] = new int[len];
	System.arraycopy(a, 0, arr2, 0, len);

	boolean result = true;
	Arrays.sort(arr2);
	sort();
	for (int i = 0; i < len; i++) {
	    if (a[i] != arr2[i]) {
		result = false;
		break;
	    }
	}
	System.out.println(result);
    }
    
    protected void swap(int x, int y) {
	int temp = a[x];
	a[x] = a[y];
	a[y] = temp;
    }
    protected abstract void sort();
}
