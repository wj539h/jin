package com.jin;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

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
    public static void main(String[] args) {
        System.out.println(new Const().getClass().getClassLoader());
        System.out.println(new HashMap<>());
        System.out.println(new ConcurrentHashMap<>());
    }
}

