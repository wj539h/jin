package com.jin.ds.sort;

import java.util.Arrays;

public abstract class AbstractSort {
	protected int arr[] = { 9, 3, 4, 1, 6, 8, 0, 7, 5, 2 };
	protected int len;

	public AbstractSort(int arr[]) {
		this.arr = arr;
		len = arr.length;
	}

	public void sortAndConPrt() {
		print();
		sort();
		print();
	}

	public void print() {
		System.out.println(Arrays.toString(arr));
	}

	public void dataCheck() {
		int arr2[] = new int[len];
		System.arraycopy(arr, 0, arr2, 0, len);

		boolean result = true;
		Arrays.sort(arr2);
		sort();
		for (int i = 0; i < len; i++) {
			if (arr[i] != arr2[i]) {
				result = false;
				break;
			}
		}
		System.out.println(result);
	}

	protected void swap(int x, int y) {
		int temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}

	protected abstract void sort();
}
