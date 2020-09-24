package com.jin.ds.sort;

import java.util.Arrays;

import com.jin.ds.array.RandomArray;

public class QuickSort extends AbstractSort {
    // { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
    public QuickSort(int arr[]) {
	super(arr);
    }

    public static void main(String[] args) {
	//int arr[] = RandomArray.randomArray(0, max - 1, max);
	int arr[] = { 9, 0, 3, 1, 2/*4, 3, 1, 9, 6, 8, 0, 7, 5, 2*/ };
	System.out.println(Arrays.toString(arr));
	quick_sort_recursive(arr, 0, arr.length - 1);
	System.out.println(Arrays.toString(arr));
    }

    @Override
    protected void sort() {
	quickSort(0, len-1);
    }
    
    void quickSort(int l, int r) {
	if(l>=r) return;
	int pp = partition(l, r);
	quickSort(l, pp-1);
	quickSort(pp+1, r);
    }
    
    int partition1(int lb, int rb) {
	int l=lb;
	int r=rb-1;
	int pv = a[rb];
	while (l<r) {
	    while (l<=r && a[l]<=pv)
		l++;
	    while (l<=r && a[r]>pv)
		r--;
	    if(l<r) {
		swap(l, r);
	    }
	}
	if(a[l]>pv)
	    swap(l, rb);
	return l;
    }
    
    int partition(int l, int r) {
	int r1 = r - 1;
	int pv = a[r];
	while (true) {
	    if (l >= r1)
		break;
		
	    
	    while (a[l] < pv && l <= r1)
		l++;
	    while (a[r1] > pv && l < r1)
		r1--;
	    
	    if (l < r1)
		swap(l, r1);
	    
	}
	if(a[l]>pv)
	    swap(l, r);
	return l;
    }

    public static void quick_sort_recursive(int arr[], int l, int r) {
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
	quick_sort_recursive(arr, l, rightP);
	quick_sort_recursive(arr, leftP, r);
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
