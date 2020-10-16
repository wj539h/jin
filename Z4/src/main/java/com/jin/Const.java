package com.jin;

import cn.hutool.core.io.file.FileReader;

import static com.jin.eudic.EudicConst.FILE_DIR_CAT_LIST;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}

    public static void main(String[] args) {
        FileReader fileReader = new FileReader("D:\\idea_workspace\\jin\\Z4\\"+FILE_DIR_CAT_LIST);
        String result = fileReader.readString();
        pln(result);
    }
}

