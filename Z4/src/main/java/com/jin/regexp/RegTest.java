package com.jin.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {
	public static void main(String[] args) {
		String str = "http://blog.sehabcpetiny.comhttp";
		Pattern p = Pattern.compile("h.*?p");
		Matcher m = p.matcher(str);
		System.out.println(m.matches());
		if(m.matches()) {
			str = m.replaceAll("jimmy");
		}
		System.out.println(str);
		
		while(m.find()) {
			System.out.println(m.start());
			System.out.println(m.end());
		}
		
		//m.reset();
		//System.out.println(m.find());
		System.out.println(m.lookingAt());
		System.out.println(m.find());
		System.out.println(m.lookingAt());
		System.out.println(m.start());
		System.out.println(m.end());
	}
	
}
