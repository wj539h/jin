package com.jin;

import java.util.concurrent.ConcurrentHashMap;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}

    public static void main(String[] args) {
        ConcurrentHashMap cMap = new ConcurrentHashMap<>();
        cMap.put(1,"1");
    }
}

