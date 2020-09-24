package com.jin.ds.array;

import java.util.Arrays;

public class SimpleArray {
    private int[] arr;// 数组对象

    /**
     * 初始化数组大小和实例
     * 
     * @param size
     */
    public SimpleArray(int size) {
	this.arr = new int[size];
    }

    /** 设置元素 */
    public void setElement(int index, int val) {
	arr[index] = val;
    }

    /** 获取元素 */
    public int getElement(int index) {
	return arr[index];
    }

    @Override
    public String toString() {
	return "SimpleArray{" + "arr=" + Arrays.toString(arr) + '}';
    }

    /**
     * 使用示例
     * 
     * @param args
     */
    public static void main(String[] args) {
	SimpleArray array = new SimpleArray(10);
	array.setElement(0, 22);
	array.setElement(1, 26);
	array.setElement(2, 20);
	array.setElement(3, 28);
	array.setElement(4, 21);
	System.out.println(array.getElement(4));
    }
}
