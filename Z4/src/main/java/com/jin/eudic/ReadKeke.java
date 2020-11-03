package com.jin.eudic;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jin.eudic.EudicConst.CRLF;

public class ReadKeke {
	public List<String> spiderWebLink() {
		List<String> result = new ArrayList<String>();
		Pattern p = Pattern.compile("http.*?daxue/.*shtml.*第?册:U[0-9]{1,2}[A|B]{0,1}");
		for(int i=1;i<=75;i++) {
			String url = i==75?"http://www.kekenet.com/daxue/16522":"http://www.kekenet.com/daxue/16522/List_"+i+".shtml";
			String r2= HttpUtil.get(url, CharsetUtil.CHARSET_UTF_8);
			Matcher m = p.matcher(r2);
			int s = 0;
			int e = 0;
			while(m.find()) {
				s = m.start();e=m.end();
				result.add(r2.substring(s, e)+CRLF);
			}
		}
		//System.out.println(result);
		return result;
	}
	
	public String spiderText(String url) {
		String str= HttpUtil.get(url, CharsetUtil.CHARSET_UTF_8);
		int s = str.indexOf("<div class=\"info-qh\">");
		int e = str.indexOf("<span style=\"display:none\" id=\"source\">");
		str = str.substring(s, e);
		str = HtmlUtil.removeHtmlTag(str,"strong");
		str = HtmlUtil.filter(str);
		str = HtmlUtil.unescape(str);
		//System.out.println(HtmlUtil.removeHtmlTag(str,"strong"));
		return str.replace("&#39;","'");
	}
	
	public List<String> generateCatalog() throws IOException {
		List<String> l = spiderWebLink();
		//String str = "http://www.kekenet.com/daxue/201612/482892.shtml\" title=\"现代大学英语精读(第2版)第二册:U1A Another School Year—What For?(2)\" target=\"_blank\">现代大学英语精读(第2版)第二册:U1A";
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		Pattern p = Pattern.compile("(h.*?shtml)(.*第)(.*册):(U[0-9]{1,2}[A|B]{0,1}.*target=)");
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
						}else if(v.equals("五册")) {
							book = "05";
						}else if(v.equals("六册")) {
							book = "06";
						}
						break;
					case 4 :
						int begin = 0;
						String arr[] = v.split(" ");
						String unit = arr[0];
						if(unit.endsWith("A") || unit.endsWith("B")) {
							if(unit.length()==3) {
								begin = 4;
								key = book+unit.replace("U", "0");
							}else if(unit.length()==4) {
								begin = 5;
								key = book+unit.replace("U", "");
							}
						}else {
							if(unit.length()==2) {
								begin = 3;
								key = book+unit.replace("U", "0");
							}else if(unit.length()==3) {
								begin = 4;
								key = book+unit.replace("U", "");
							}
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
		
		//System.out.println(map);
		Set<Map.Entry<String, List<String>>> set = map.entrySet();
		Iterator<Map.Entry<String, List<String>>> it = set.iterator();
		l = new ArrayList<String>();
		while(it.hasNext()) {
			String v = "";
			Map.Entry<String, List<String>> me = it.next();
			l.add(me.getKey());
			List<String> list = me.getValue();
			for (String s : list) {
				l.add(s);
			}
			l.add("--------------");
		}
		return l;
	}
	
	public void sort() throws IOException {
		List<String> l = generateCatalog();
		Pattern p = Pattern.compile("\\d{4}[A|B]{0,1}");
        ListOrderedMap<String, List<String>> map = new ListOrderedMap<String, List<String>>();
        List<String> list = null;
        String catalog = null;
		for(String s:l) {
			Matcher m = p.matcher(s);
			//m.region(0, 5);
			if(m.matches()) {
				list = map.get(s) == null?new ArrayList<String>():map.get(s);
				catalog = s;
			} else if(StringUtils.isNotEmpty(s) && !StringUtils.startsWith(s, "---")) {
				list.add(s);
			} else if(StringUtils.startsWith(s, "---")) {
				map.put(catalog, list);
			}
		}
		Object objArr[] = map.keyList().toArray();
		Arrays.sort(objArr);
		ListOrderedMap<String, List<String>> newMap = new ListOrderedMap<String, List<String>>();
		for (int i = 0; i < objArr.length; i++) {
			String cata = (String)objArr[i];
			list = map.get(cata);
			String tmpA[] = new String[30];
			for(String str : list) {
				String ta[] = str.split(" \\| ");
				String title = ta[1].substring(ta[1].lastIndexOf("(")+1, ta[1].length()-1);
				tmpA[Integer.valueOf(title)-1] = str;
			}
			newMap.put(cata,Arrays.asList(tmpA));
		}
		
		for (Map.Entry<String, List<String>> me : newMap.entrySet()) {
			String content = me.getKey()+CRLF;
			String text = "";
			List<String> l1 = me.getValue();
			for(String str : l1) {
				if(str!=null) {
					String ta[] = str.split(" \\| ");
					//System.out.println(ta[1]+" | "+ta[0]);
					content+=ta[1]+" | "+ta[0]+CRLF;
					text+=spiderText(ta[0])+CRLF;
				}
			}
			content+=text+CRLF;
			System.out.println(me.getKey()+"---------------------------------------------------------------");
			FileUtils.writeStringToFile(new File("d:/rb1"), content, "utf-8", true);
		}
	}

	public static void main(String[] args) {
		ReadKeke r = new ReadKeke();
		try {
			r.sort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
