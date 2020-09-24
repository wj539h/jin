package com.jin.ds.sort;

import com.jin.ds.array.RandomArray;

public class SimpleSort extends AbstractSort {
    public SimpleSort(int arr[]) {
	super(arr);
    }

    @Override
    protected void sort() {
	// bubbleSort();
	// selectSort();
	 insertSort1();
	// shellSort();
    }

    public static void main(String[] args) {
	int max = 100000;
	int randomArr[] = RandomArray.randomArray(0, max - 1, max);
	int temp[] = { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
	AbstractSort as = new SimpleSort(randomArr);
	//as.consolePrint();
	as.timeLogPrint();
	//as.dataCheck();
    }

    
    // 9, 3, 4, 1
    // ----------------------------------
    void insertSort1() {
	for (int i = 1; i < len; i++) {
	    int temp = a[i];
	    int j = i;
	    while (j > 0 && temp < a[j - 1]) {
		a[j] = a[j - 1];
		j--;
	    }
	    a[j] = temp;
	}
    }
    void insertSort2() {
	for (int i = 1; i < len; i++) {
	    for (int j = i; j > 0; j--) {
		if (a[j - 1] > a[j]) {
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
		if (a[j] > a[j + 1]) {
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
		if (a[maxPos] < a[j]) {
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
		    int temp = a[i];
		    int j = i;
		    while (j > k && temp < a[j - gap]) {
			a[j] = a[j - gap];
			j -= gap;
		    }
		    a[j] = temp;
		}
	    }
	    gap /= 2;
	}

    }
    // ----------------------------------

}
