package com.jin;

import cn.hutool.core.io.file.FileReader;
import com.jin.eudic.EudicOpt;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}
    
    public static void main(String[] args) {
        FileReader fileReader = new FileReader(EudicOpt.FILE_DIR_CAT_LIST);
        String result = fileReader.readString();
        pln(result);
    }
}

