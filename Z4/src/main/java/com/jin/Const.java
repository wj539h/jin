package com.jin;

import static com.jin.eudic.EudicConst.CRLF;
import static com.jin.eudic.EudicConst.FILE_DIR_NOTE_TEMP;
import static com.jin.eudic.EudicConst.LINE_JOINNER;
import static com.jin.eudic.EudicConst.UTF8;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.io.file.FileReader;

public class Const {
    public static final String full_sap = "-----------------------------------------------------------------------------";
    public static final String half_sap = "----------------------------------";
    public static void pln(){System.out.println();}
    public static void pln(Object content){System.out.println(content);}
    public static void p(Object content){System.out.print(content);}
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String LINE_JOINNER = "||";
    public static void main(String[] args) {
    }
}

