package com.jin.ds.array;

public class OrderArray implements ArrayInterface {
    public int[] arr;
    public int size;// 已有元素数量

    public OrderArray(int maxsize) {
	this.arr = new int[maxsize];
	size = 0;
    }

    /**
     * 这里是有序数组 线性查找示例 找到当前元素适合插入的问题 随后对元素进行移动操作
     * 
     * @param val
     */
    @Override
    public void insert(int val) {
	// 查找适合的位置
	int realpostion = 0;
	for (realpostion = 0; realpostion < size; realpostion++) {
	    if (arr[realpostion] > val)
		break;// 如果当前元素比val大，则终止循环（这里默认是有序递增形式数组）
	}
	// 进行元素置换 k是元素数量 比下标错开1位 下标从0 开始 所以k 其实对应下标+1 这样子
	for (int k = size; k > realpostion; k--) {
	    arr[k] = arr[k - 1];
	}
	arr[realpostion] = val;
	size++;
    }

    /**
     * 二分查找
     * 
     * @param val
     * @return
     */
    @Override
    public int find(int val) {
	int first = 0;// 二分当前范围的起始位置
	int last = size - 1;// 二分当前范围的结束位置
	int mid;// 二分当前范围的中间值

	while (true) {
	    mid = (first + last) / 2;
	    if (arr[mid] == val) {// 如果刚好与查找的一致，则返回下标
		return mid;
	    } else if (first > last) {// 未查到则返回数组大小
		return size;
	    } else {
		// 当前值大于中间下标
		if (arr[mid] < val) {
		    first = mid + 1;
		} else {
		    last = mid - 1;
		}
	    }
	}
    }

    @Override
    public int size() {
	return size;
    }

    /**
     * 数组删除 并移动数组元素
     * 
     * @param val
     */
    @Override
    public void del(int val) {
	int valindex = find(val);
	if (valindex == size) {// 未查到下标
	    System.out.println("未查到");
	} else {
	    for (int k = valindex; k < size; k++) {// 移动后边数组
		arr[k] = arr[k + 1];
	    }
	    size--;
	    System.out.println("查到了，进行数组移动...");
	}
    }

    static boolean b;

    public static void main(String[] args) {
	OrderArray orderArray = new OrderArray(10);
	orderArray.insert(3);
	orderArray.insert(6);
	orderArray.insert(5);
	orderArray.insert(4);
	orderArray.insert(1);
	orderArray.displayAll(orderArray.arr);
	orderArray.del(4);
	orderArray.displayAll(orderArray.arr);
	
	int x = 0;
	if (b) {
	    x = 1;
	} else if (b = false) {
	    x = 2;
	} else if (b) {
	    x = 3;
	} else {
	    x = 4;
	}
	System.out.println("x:" + x);
	 
    }
}
