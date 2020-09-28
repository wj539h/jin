package com.jin.ds.sort;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.jin.ds.TimerCallBack;
import com.jin.ds.array.RandomArray;

public class QuickSort extends AbstractSort {
    // { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
    public QuickSort(int arr[]) {
	super(arr);
    }

    public static void main(String[] args) {
	int max = 10000;
	//int arr[] = RandomArray.randomArray(0, max - 1, max);
	int arr[] = RandomArray.genOrderedArr(0, max - 1, 1);
	//int arr[] = { /*1, 0, 3, 9, 2*/4, 3, 1, 9, 6, 8, 0, 7, 5, 2 };
	QuickSort qs = new QuickSort(arr);
	TimerCallBack.timerPrint(TimeUnit.MICROSECONDS, new TimerCallBack() {
	    public void doSomething() {
		//qs.qs_rec_left_pivot(0, arr.length-1);
		qs.sort();
	    }
	});
	//System.out.println(Arrays.toString(arr));
    }

    @Override
    protected void sort() {
	qs_rec_left_pivot(0, len-1);
	//qs_rec_right_pivot(0, len-1);
    }
    
    
    

    public void qs_rec_left_pivot(int l, int r) {
	int pivot = l;
	int leftP = l + 1, rightP = r;
	if(leftP>=rightP) 
	    return;
	
	while (true) {
	    while (arr[leftP] < arr[pivot] && leftP < r) {
		leftP++;
	    }
	    while (arr[rightP] > arr[pivot]) {
		rightP--;
	    }
	    if(leftP < rightP)
		swap(arr, leftP, rightP);
	    else {
		if(rightP > pivot)
		    swap(arr, rightP, pivot);
		break;
	    }
	}
	qs_rec_left_pivot(l, rightP);
	qs_rec_left_pivot(leftP, r);
    }
    public void qs_rec_right_pivot(int l, int r) {
	int p = r, lb = l, rb = r-1;
	if(lb >= rb)
	    return;
	while(true) {
	    while(lb<rb && arr[lb]<arr[p] ) {
		lb++;
	    }
	    while(lb<rb && arr[rb]>arr[p]) {
		rb--;
	    }
	    if (lb >= rb) {
		if( arr[lb] > arr[p])
		    swap(arr, lb, p);
		break;
	    } else {
		swap(arr, lb, rb);
	    }
	}
	qs_rec_right_pivot(l, lb);
	qs_rec_right_pivot(rb, r);
    }
    
    public static void swap(int arr[], int x, int y) {
	arr[x] = arr[x] ^ arr[y];
	arr[y] = arr[x] ^ arr[y];
	arr[x] = arr[x] ^ arr[y];
	/*arr[x] = arr[x] + arr[y];
	arr[y] = arr[x] - arr[y];
	arr[x] = arr[x] - arr[y];
	arr[x] = arr[x] - arr[y];
	arr[y] = arr[x] + arr[y];
	arr[x] = arr[y] - arr[x];*/
    }
}
