package com.jin.eudic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;

public class ReadKeke {
	public void spiderWeb() {
		Pattern p = Pattern.compile("http.*?daxue/.*shtml.*第?册:U[0-9]{1,2}[A|B]");
		for(int i=1;i<=74;i++) {
			String r2= HttpUtil.get("http://www.kekenet.com/daxue/16522/List_"+i+".shtml", CharsetUtil.CHARSET_UTF_8);
			Matcher m = p.matcher(r2);
			int s = 0;
			int e = 0;
			while(m.find()) {
				s = m.start();e=m.end();
				System.out.println(r2.substring(s, e));
			}
		}
	}
	
	public void generateCatalog() throws IOException {
		List<String> l = FileUtils.readLines(new File("d:/rb"), "utf-8");
    	Map<String,List<String>> map = new HashMap<String,List<String>>();
		//String str = "http://www.kekenet.com/daxue/201612/482892.shtml\" title=\"现代大学英语精读(第2版)第二册:U1A Another School Year—What For?(2)\" target=\"_blank\">现代大学英语精读(第2版)第二册:U1A";
		Pattern p = Pattern.compile("(h.*?shtml)(.*第)(.*册):(U[0-9]{1,2}[A|B].*target=)");
		for (String line : l) {
			Matcher m = p.matcher(line);
			m.find();
			//System.out.println(m.find()+"该次查找获得匹配组的数量为：" + m.groupCount()); // 2
			String url = "";
			String book = "";
			String key = "";
			List<String> list = null;
			for (int i = 1; i <= m.groupCount(); i++) {
				String v = m.group(i);
				switch(i) {
					case 1 :
						url = v;
						break;
					case 3 :
						if(v.equals("一册")) {
							book = "01";
						}else if(v.equals("二册")) {
							book = "02";
						}else if(v.equals("三册")) {
							book = "03";
						}else if(v.equals("四册")) {
							book = "04";
						}
						break;
					case 4 :
						int begin = 0;
						String arr[] = v.split(" ");
						if(arr[0].length()==3) {
							begin = 4;
							key = book+arr[0].replace("U", "0");
						}else if(arr[0].length()==4) {
							begin = 5;
							key = book+arr[0].replace("U", "");
						}
						list = map.get(key) == null? new ArrayList<String>() : map.get(key);
						int end = v.indexOf("\"");
						String content = url+" | "+v.subSequence(begin, end).toString();
						list.add(content);
						map.put(key, list);
						break;
					default:
				}
			}
		}
		
		System.out.println(map);
		Set<Map.Entry<String, List<String>>> set = map.entrySet();
		Iterator<Map.Entry<String, List<String>>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, List<String>> me = it.next();
			System.out.println(me.getKey());
			System.out.println();
			List<String> list = me.getValue();
			for (String s : list) {
				System.out.println(s);
			}
			System.out.println();
			System.out.println("--------------------------------------------------------");
		}
	}
	
	public static void main(String[] args) {
		
	}
}
