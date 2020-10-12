package com.jin.ds.recursive;

import com.jin.ds.array.RandomArray;
import com.jin.ds.sort.AbstractSort;

public class RecursivShellSort extends AbstractSort {
    public RecursivShellSort(int[] arr) {
	super(arr);
    }

    @Override
    protected void sort() {
	int gap = calGap(1);
	shellSort(gap);
    }

    public static void main(String[] args) {
	int max = 15;
	int randomArr[] = RandomArray.randomArray(0, max - 1, max);
	//int temp[] = { 9, 3, 1, 4, 6, 8, 0, 7, 5, 2 };
	AbstractSort as = new RecursivShellSort(randomArr);
	as.sortAndConPrt();
    }
    
    public void shellSort(int gap) {
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
	if(gap > 0) {
	    shellSort(gap);
	}
    }
    
    //最佳的间隔h=1, h=3*h+1
    public int calGap(int gap) {
	int result = 3*gap+1;
	if(result > len)
	    result = gap;
	else
	    result = calGap(3*gap+1);
	return result;
    }
}
