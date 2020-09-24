package com.jin.regexp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CallingCountryCode {
	public static void main(String[] args) throws Exception {
		
		//ALTER TABLE XADDRESSINFO ADD CALLINGCOUNTRYCODE VARCHAR(12);
		//alter table XADDRESSINFO drop column CALLINGCOUNTRYCODE; 
		
		File a = new File("D:\\workspace1\\First\\src\\com\\regexp\\a");
		Reader re = new FileReader(a);
		BufferedReader br = new BufferedReader(re);
		String str = null;
		while((str = br.readLine()) != null) {
			readB(str); 
		}
		List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        if(str==null) {
        	System.out.println(list);
        }
       /* Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            Integer integer = iterator.next();
            if(integer==2)
                list.remove(integer);
        }*/
	}
	static void readB(String digit) throws Exception {
		String digitArr[] = new String(digit).split("\t");
		StringBuffer result = new StringBuffer();
		File b = new File("D:\\workspace1\\First\\src\\com\\regexp\\b");
		Reader re = new FileReader(b);
		BufferedReader br = new BufferedReader(re);
		String str = null;
		while((str = br.readLine()) != null) {
			String strArr[] = str.split("  ");
			if( strArr[1].equals(digitArr[1])) {
				result.append(digit).append("\t").append(strArr[0]);
				System.out.println(result);
			}
		}
	}
	static void readD(String digit) throws Exception {
		String digitArr[] = new String(digit).split("\t");
		StringBuffer result = new StringBuffer();
		File b = new File("D:\\workspace1\\First\\src\\com\\regexp\\d");
		Reader re = new FileReader(b);
		BufferedReader br = new BufferedReader(re);
		String str = null;
		int count = 0;
		while((str = br.readLine()) != null) {
			String strArr[] = str.split("\t");
			if( strArr[2].equals(digitArr[2])) {
				System.out.println("update XADDRESSINFO set CALLINGCOUNTRYCODE = '"+digitArr[0]+"' where COUNTRYCODE='"+strArr[0]+"' and STORE_ID='"+strArr[1]+"';");
			}
		}
	}
	static void readE(String digit) throws Exception {
		String digitArr[] = new String(digit).split("\t");
		StringBuffer result = new StringBuffer();
		File b = new File("D:\\workspace1\\First\\src\\com\\regexp\\e");
		Reader re = new FileReader(b);
		BufferedReader br = new BufferedReader(re);
		String str = null;
		int count = 0;
		while((str = br.readLine()) != null) {
			String strArr[] = str.split(",");
			if( strArr[2].contains(digitArr[2])) {
				System.out.println(str+",\""+digitArr[0]+"\"");
			}
		}
	}
}
