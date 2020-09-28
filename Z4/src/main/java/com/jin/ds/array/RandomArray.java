package com.jin.ds.array;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.jin.ds.TimerCallBack;

public class RandomArray {
    public static void main(String[] args) throws IOException {
	int max = 1000000;
	//int dupArr[] = genDupArr(max);
	//int noDupArr[] = genNoDupArr(max);
	 //int noDupArr[] = randomCommon(1, max, max-1);
	//int noDupArr[] = randomArray(0, max - 1, max);
	//int noDupArr[] = randomArray(0, max - 1, max);
	TimerCallBack.timerPrint(TimeUnit.MICROSECONDS, new TimerCallBack() {
	    public void doSomething() {
		randomArray(0, max - 1, max);
	    }
	});
	//System.out.println(Arrays.toString(noDupArr));
	//check(noDupArr);
    }
    
    public static int[] genOrderedArr(int min, int max, int ascOrDesc) {
	int len = max - min + 1;

	int[] source = new int[len];
	if(ascOrDesc == 0) {
	    for (int i = min; i < min + len; i++) {
		source[i - min] = i;
	    }
	} else {
	    for (int i = min; i < min + len; i++) {
		source[i - min] = max-i+min;
	    }
	}
	
	return source;
    }
    
    /**
               * 这个是生成重复的，只是分配空间，只是分配空间的时间
     * @param len
     * @return
     */
    public static int[] genRandomInRange(int min, int max, int len) {
	int arr[] = new int[len];
	for (int i = 0; i < len; i++) {
	    int temp = (int) (Math.random() * 100);
	    arr[i] = temp % (max - min + 1) + min;
	}
	return arr;
    }

    public static int[] genRandomInRange1(int min, int max, int len) {
	int arr[] = new int[len];
	Random r = new Random();
	for (int i = 0; i < len; i++) {
	    int temp = r.nextInt(max) % (max - min + 1);
	    arr[i] = temp % (max - min + 1) + min;
	}
	return arr;
    }
    

    /**
     * 这个是我自己写的，用起来还可以，比randomCommon好用
     * @param len
     * @return
     */
    public static int[] genNoDupArr(int len) {
	int arr[] = new int[len];
	for (int i = 0; i < len; i++) {
	    arr[i] = -1;
	}
	Random r = new Random();
	int pos = -1;
	int oldPos = -1;
	int nextInt = r.nextInt(len);
	for (; (pos = containsInt(arr, nextInt)) != -1; nextInt = r.nextInt(len)) {
	    if (oldPos >= pos)
		continue;
	    arr[pos] = nextInt;
	    oldPos = pos;
	    if (pos == (len - 1))
		break;
	}
	return arr;
    }

    private static int containsInt(int arr[], int nextInt) {
	int pos = -1;
	for (int i = 0; i < arr.length; i++) {
	    if (arr[i] == nextInt)
		return i;
	    if (arr[i] == -1) {
		pos = i;
		break;
	    }
	}
	return pos;
    }

    /**
     * 相当牛逼的方法，上来先弄一个有序数组和空数组，再把有序数组根据%len然后乘以随机数去填充到空数组
     * 空间换时间
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static int[] randomArray(int min, int max, int n) {
	int len = max - min + 1;

	if (max < min || n > len) {
	    return null;
	}

	int[] source = new int[len];
	for (int i = min; i < min + len; i++) {
	    source[i - min] = i;
	}

	int[] result = new int[n];
	Random rd = new Random();
	int index = -1;
	for (int i = 0; i < result.length; i++) {
	    index = Math.abs(rd.nextInt() % len--);
	    /*switch (i) {
	    case 0: index = 2; break;
	    case 1: index = 4; break;
	    case 2: index = 6; break;
	    case 3: index = 1; break;
	    case 4: index = 2; break;
	    case 5: index = 2; break;
	    case 6: index = 1; break;
	    case 7: index = 0; break;
	    case 8: index = 1; break;
	    case 9: index = 0; break;
	    	default:}
	    System.out.println("case "+i+": index = "+index+"; break;");len--*/
	    result[i] = source[index];
	    source[index] = source[len];//精髓，把%len-1放到已保存的数的位置，因为根据%的概念，随着len--，找到的随机数不可能>=len

	}
	return result;
    }
    
    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
	if (n > (max - min + 1) || max < min) {
	    return;
	}
	for (int i = 0; i < n; i++) {
	    // 调用Math.random()方法
	    int num = (int) (Math.random() * (max - min)) + min;
	    set.add(num);// 将不同的数存入HashSet中
	}
	int setSize = set.size();
	// 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
	if (setSize < n) {
	    randomSet(min, max, n - setSize, set);// 递归
	}
    } 

    /**
     * 跟我写的差不多，不过略有瑕疵，思路一样
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static int[] randomCommon(int min, int max, int n) {
	if (n > (max - min + 1) || max < min) {
	    return null;
	}
	int[] result = new int[n];
	int count = 0;
	while (count < n) {
	    int num = (int) (Math.random() * (max - min)) + min;
	    boolean flag = true;
	    for (int j = 0; j < n; j++) {
		if (num == result[j]) {
		    flag = false;
		    break;
		}
	    }
	    if (flag) {
		result[count] = num;
		count++;
	    }
	}
	return result;
    }

    public static void check(int arr[]) {
	Set set = new HashSet();
	for (int i = 0; i < arr.length; i++) {
	    set.add(arr[i]);
	}
	if (set.size() == arr.length) {
	    System.out.println(Boolean.TRUE);
	} else {
	    System.out.println(Boolean.FALSE);

	}
    }
}
