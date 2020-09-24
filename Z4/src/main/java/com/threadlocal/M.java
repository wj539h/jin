package com.threadlocal;

import com.jin.ds.array.RandomArray;

public class M {
    private String refName;

    public M() {
    }

    public M(String refName) {
	super();
	this.refName = refName;
    }

    @Override
    protected void finalize() throws Throwable {
	System.out.println(refName + "=====finalize!!!!!!!!");
    }

    public String getRefName() {
	return refName;
    }

    public void setRefName(String refName) {
	this.refName = refName;
    }
    
    public <T> void printMsg(T... args){
        for (T t:args){
            System.out.println("泛型测试:"+t);
        }
        for (int i=0;i<args.length;i++){
            System.out.println("测试:"+args[i]);
        }//结论:可以通过遍历数组的方式遍历数组,可变参数是利用数组实现的
    }
    
    public static int f(int n) {
	int r = 0;
	if(n==1)
	    r=1;
	else if(n==2)
	    r=2;
	else
	    r=f(n-2)+f(n-1);
	return r;
    }

    public static void main(String[] args) {
	int max = 10000;
	int randomArr[] = RandomArray.randomArray(0, max - 1, max);
	M m = new M();
	long result = 0;
	long start = System.nanoTime();
	for(int i=0;i<randomArr.length;i++) {
	    result+= randomArr[i];
	}
	System.out.println(result);
	long end = System.nanoTime();
	System.out.println(end-start);
	
	start = System.nanoTime();
	int bound = max/m.range;
	for(int i=0;i<=m.range;i++) {
	    final int s = i*bound;
	    final int e = (i==m.range) ? (randomArr.length-1) : (s+bound-1);
	    final int n = i;
	    Thread t = new Thread(()-> {
		m.sumAmount(randomArr, s, e, n);
	    });
	    t.start();
	    try {t.join();} catch (InterruptedException e1) {e1.printStackTrace();}
	}
	result = 0;
	for(int i=0;i<m.resultArr.length;i++) {
	    result+=m.resultArr[i];
	}
	System.out.println(result);
	end = System.nanoTime();
	System.out.println(end-start);
    }
    
    private int range = 50;
    private int resultArr[] = new int[range+1];
    public void sumAmount(int arr[], int start, int end, int n) {
	int result = 0;
	for(int i=start;i<=end;i++) {
	    result+= arr[i];
	}
	System.out.println(Thread.currentThread().getName()+"-n :"+n+"-result : "+result+"-start : "+start+"-end : "+end);
	resultArr[n]=result;
    }
}



