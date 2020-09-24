package com.jin.ds.array;

public class SeniorArray {
    private int[] arr;// 具体数组类型
    private int totalsize;// 已有数组数量

    /**
     * 创建数组实例，并设置大小
     * 
     * @param size
     */
    public SeniorArray(int size) {
	this.arr = new int[size];
	this.totalsize = 0;
    }

    /**
     * 添加数组元素
     * 
     * @param val
     */
    public void insert(int val) {
	arr[totalsize++] = val;
    }

    /**
     * 删除数组元素
     * 
     * @param val
     */
    public void del(int val) {
	int delindex = findIndex(val);
	if (delindex < totalsize) {
	    for (int i = delindex; i < totalsize; i++) {
		arr[i] = arr[i + 1];
	    }
	    totalsize--;
	}
    }

    /**
     * 查找数组元素
     * 
     * @param val
     */
    public int findIndex(int val) {
	int searchIndex = totalsize;// 默认是元素数量
	for (int i = 0; i < arr.length; i++) {
	    if (arr[i] == val) {
		searchIndex = i;
		break;
	    }
	}
	System.out.println("val:" + val + ",index:" + searchIndex);
	return searchIndex;
    }

    /**
     * 查找数组元素
     * 
     * @param index
     */
    public int findByIndex(int index) {
	return arr[index++];
    }

    /**
     * 遍历 数组元素
     */
    public void display() {
	for (int i = 0; i < totalsize; i++) {
	    System.out.print(arr[i] + ",");
	}
	System.out.println("----------------------------------");
    }

    public static void main(String[] args) {
	SeniorArray seniorArray = new SeniorArray(10);
	seniorArray.insert(2);
	seniorArray.insert(5);
	seniorArray.insert(3);
	seniorArray.insert(1);
	seniorArray.insert(42);
	seniorArray.insert(6);
	System.out.println(seniorArray.totalsize);
	seniorArray.display();
	seniorArray.del(1);
	seniorArray.display();
    }
}
