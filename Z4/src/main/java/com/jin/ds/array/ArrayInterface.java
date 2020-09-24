package com.jin.ds.array;

import java.util.Arrays;

public interface ArrayInterface {
    void insert(int val);// 插入

    int find(int val);// 查找

    int size();// 已有元素数量

    void del(int val);// 删除

    /**
     * 默认显示array方法
     * 
     * @param arr
     */
    default void displayAll(int[] arr) {
	int total = size();
	Arrays.stream(arr).limit(total).forEach(a -> {
	    System.out.println(a);
	});
    }
}
