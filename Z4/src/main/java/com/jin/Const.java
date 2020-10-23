package com.jin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}

	public static void main(String[] args) throws Exception {
		String str = "http://blog.sehabcpetiny.comhttp";
		Pattern p = Pattern.compile("^ht.*g$");
		Matcher m = p.matcher(str);
		System.out.println(m.matches());
		if (m.matches()) {
			str = m.replaceAll("jimmy");
		}
		System.out.println(str);

	}
}

