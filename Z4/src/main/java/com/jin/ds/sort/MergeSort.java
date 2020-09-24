package com.jin.ds.sort;

import java.util.Arrays;

import com.jin.ds.array.RandomArray;

//https://zhuanlan.zhihu.com/p/124356219
public class MergeSort {
    public static void main(String[] args) {
	int max = 430;

	//int arr[] = RandomArray.randomArray(0, max - 1, max);
	int arr[] = { 9,0,3,1,2/*4, 3, 1, 9, 6, 8, 0, 7, 5, 2*/ };
    }
    
    static void merge_sort_recursive(int[] arr, int[] result, int start, int end) {
	if (start < end) {
	    int mid = (end - start) / 2 + start;
	    int ls = start;
	    int le = mid;
	    int rs = mid + 1;
	    int re = end;
	    
	    merge_sort_recursive(arr, result, ls, le);
	    merge_sort_recursive(arr, result, rs, re);
	    
	    int k = ls;
	    while(ls<=le && rs<=re) {
		result[k++] = arr[ls]<arr[rs] ? arr[ls++] : arr[rs++];
	    }
	    while(ls<=le) {
		result[k++] = arr[ls++];
	    }
	    while(rs <= re) {
		result[k++] = arr[rs++];
	    }
	    for(int i=start;i<k;i++) {
		arr[i] = result[i];
	    }
	}
    }
    
    static void merge_sort(int[] arr, int[] result) {
	int len = arr.length;
	int block, start;
	// 原版代码的迭代次数少了一次，没有考虑到奇数列数组的情况
	for (block = 1; block < len * 2; block *= 2) {
	    for (start = 0; start < len; start += 2 * block) {
		int low = start;
		int mid = (start + block) < len ? (start + block) : len;
		int high = (start + 2 * block) < len ? (start + 2 * block) : len;
		//两个块的起始下标及结束下标
		int start1 = low, end1 = mid;
		int start2 = mid, end2 = high;
		//开始对两个block进行归并排序
		while (start1 < end1 && start2 < end2) {
		    result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
		}
		while (start1 < end1) {
		    result[low++] = arr[start1++];
		}
		while (start2 < end2) {
		    result[low++] = arr[start2++];
		}
	    }
	    int[] temp = arr;
	    arr = result;
	    result = temp;
	}
	result = arr;
    }
    
    static void merge(int arr[],int s,int e) {
	if(s>=e)
	    return;
	//int s = 0, e=len-1, mid = (e-s)/2, s1 = mid + 1;
	int mid = ((e-s)/2)+s, s1 = mid + 1, len = e-s+1,oriS=s;
	merge(arr, s, mid);
	merge(arr, s1, e);
	int tempArr[] = new int[len];
	int pos = 0;
	while(true) {
	    if(arr[s]<arr[s1] && s<=mid) {
		tempArr[pos++] = arr[s++];
	    }
	    if(arr[s]>arr[s1] && s1<=e) {
		tempArr[pos++] = arr[s1++];
	    }
	    if(s>mid || s1>e) {
		break;
	    }
	}
	if (s > mid) {
	    for (int i = s1; i <= e; i++) {
		tempArr[pos++] = arr[i];
	    }
	}
	if (s1 > e) {
	    for (int i = s; i <= mid; i++) {
		tempArr[pos++] = arr[i];
	    }
	}
	//System.out.println(Arrays.toString(tempArr));
	System.arraycopy(tempArr, 0, arr, oriS, len);
    }
}
