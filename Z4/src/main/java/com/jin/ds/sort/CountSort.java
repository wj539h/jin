package com.jin.ds.sort;

import com.jin.ds.array.RandomArray;

public class CountSort extends AbstractSort {
	// { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
	public CountSort(int arr[]) {
		super(arr);
	}

	private int min = 0;
	private int gap = 10;

	public static void main(String[] args) {
		int max = 100;
		int randomArr[] = RandomArray.genRandomInRange(15, 20, max);
		int temp[] = { 20, 15, 15, 17, 16, 18, 18, 18, 19, 19, 16, 20 };
		CountSort as = new CountSort(randomArr);
		as.setMin(15);
		as.setGap(5);
		as.sortAndConPrt();
		//as.timeLogPrint();
	}

	@Override
	protected void sort() {
		int countArr[] = new int[gap + 1];
		for (int i = 0; i < len; i++) {
			countArr[arr[i] - 15]++;
		}

		int newN = 0;
		for (int i = 0; i < countArr.length; i++) {//221322
			int v = i + min;
			while (countArr[i]-- > 0) {
				arr[newN++] = v;
			}
		}
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}
}
