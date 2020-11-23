package com.jin;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UTF8 = "UTF-8";
    public static final String LINE_JOINNER = "||";
    public static final String LINE_SPLITTER = "\\|\\|";
    public static final String WORD_JOINNER = "!@#";
    public static final String HTML_BR = "<br>";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}
    public static void sleep1000(){
        try {TimeUnit.MILLISECONDS.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
    }
    public static void sleep500(){
        try {TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) {e.printStackTrace();}
    }
    public static void sleep(long millisec){
        try {TimeUnit.MILLISECONDS.sleep(millisec); } catch (InterruptedException e) {e.printStackTrace();}
    }
    public static void sleepThrow(long millisec) throws InterruptedException{
    	TimeUnit.MILLISECONDS.sleep(millisec);
    }
    public static String tName() {
        return Thread.currentThread().getName();
    }
    public static void tJoin(Thread t) {
        try {t.join();} catch (InterruptedException e) {e.printStackTrace();}
    }
    public static <E> List<E> getDuplicateElements(List<E> list) {
		return list.stream() // list 对应的 Stream
				.collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
				.entrySet().stream() // 所有 entry 对应的 Stream
				.filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
				.map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
				.collect(Collectors.toList());  // 转化为 List
	}
    public static void main(String[] args) {
        /*System.out.println(new Const().getClass().getClassLoader());
	System.out.println(new HashMap<>());
	System.out.println(new ConcurrentHashMap<>());*/
		HashMap hmap = new HashMap();
		ConcurrentHashMap cmap = new ConcurrentHashMap();
		hmap.put(1, "1");
		cmap.put(17, "170");
		cmap.put(33, "330");
		cmap.put(49, "490");
		cmap.put(65, "650");
		cmap.put(81, "810");
		cmap.put(97, "970");
		cmap.put(113, "1130");
		cmap.put(129, "1290");//当添加到这个元素的时候进行扩容
		cmap.put(145, "1450");
		cmap.put(161, "1610");
		cmap.put(177, "1770");
		cmap.put(193, "1930");
		cmap.put(209, "2090");
		cmap.put(241, "2410");
		cmap.put(257, "2570");
    }
}

