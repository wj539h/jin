package com.jin.regexp;

public class CharacterTest {
    static String count;

    public static void main(String[] args) throws Exception {
	/*String str="顾";
	byte bArr[]=str.getBytes("utf-16");
	for (int i = 0; i < bArr.length; i++) {
		System.out.println(bArr[i]);
	}*/
	for (int i = 0; i < 3; i++) {
	    new Thread(new TestTask(i)).start();
	}
    }

    public static class TestTask implements Runnable {
	int id;

	public TestTask(int id) {
	    this.id = id;
	}

	public void run() {
	    count = "t-" + id;
	    System.out.println(Thread.currentThread().getName() + " : Num -- " + count);
	}
    }
}
