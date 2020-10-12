package com.jin.ds.sort;

import java.util.concurrent.TimeUnit;

import com.jin.ds.TimerCallBack;
import com.jin.ds.array.RandomArray;

public class SimpleSort extends AbstractSort {
    public SimpleSort(int arr[]) {
	super(arr);
    }

    @Override
    protected void sort() {
	//bubbleSort();
	//selectSort();
	insertSort1();
	//shellSort();
    }

    public static void main(String[] args) {
	int max = 10000;
	//int arr[] = RandomArray.randomArray(0, max - 1, max);
	int arr[] = RandomArray.genOrderedArr(0, max - 1, 1);
	//int arr[] = { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
	AbstractSort as = new SimpleSort(arr);
	//as.sort();
	TimerCallBack.timerPrint(TimeUnit.MICROSECONDS, new TimerCallBack() {
	    public void doSomething() {
		as.sort();
	    }
	});
	
    }

    
    // 9, 3, 4, 1
    // ----------------------------------
    void insertSort1() {
	for (int i = 1; i < len; i++) {
	    int temp = arr[i];
	    int j = i;
	    while (j > 0 && temp < arr[j - 1]) {
		arr[j] = arr[j - 1];
		j--;
	    }
	    arr[j] = temp;
	}
    }
    //上面的insertSort1比这insertSort2效率高很多, 尽量拷贝,减少交换
    void insertSort2() {
	for (int i = 1; i < len; i++) {
	    for (int j = i; j > 0; j--) {
		if (arr[j - 1] > arr[j]) {
		    swap(j - 1, j);
		}
	    }
	}
    }
    // ----------------------------------

    // ----------------------------------
    void bubbleSort() {
	for (int i = len; i > 0; i--) {
	    for (int j = 0; j < i - 1; j++) {
		if (arr[j] > arr[j + 1]) {
		    swap(j, j + 1);
		}
	    }
	}
    }
    // ----------------------------------

    // ----------------------------------
    public void selectSort() {
	for (int i = len; i > 0; i--) {
	    int maxPos = 0;
	    for (int j = 1; j < i; j++) {
		if (arr[maxPos] < arr[j]) {
		    maxPos = j;
		}
	    }
	    swap(maxPos, i - 1);
	}
    }
    // ----------------------------------

    // ----------------------------------
    public void shellSort() {
	// h = 1
	// h = 3*h + 1
	int gap = 4;

	while (gap > 0) {
	    for (int k = 0; k < gap; k++) {
		for (int i = gap + k; i < len; i += gap) {
		    int temp = arr[i];
		    int j = i;
		    while (j > k && temp < arr[j - gap]) {
			arr[j] = arr[j - gap];
			j -= gap;
		    }
		    arr[j] = temp;
		}
	    }
	    gap /= 2;
	}

    }
    // ----------------------------------

}
