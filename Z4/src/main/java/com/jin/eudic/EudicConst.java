package com.jin.eudic;

public class EudicConst {
    public static final String URL_CATEGORY_QUERY = "https://api.frdic.com/api/open/v1/studylist/category";
    public static final String URL_WORD = "https://api.frdic.com/api/open/v1/studylist/words/";
    public static final String URL_CAT_WORD_OPT = "http://my.eudic.net/StudyList/Edit";
    public static final String URL_WORD_LOOK_FOR = "https://dict.eudic.net/dicts/en/";
    public static final String URL_CUSTOMIZE_INFO = "https://dict.eudic.net/dicts/CustomizeInfo";
    public static final String URL_SET_NOTE = "https://dict.eudic.net/Dicts/SetNote";

    public static final String FILE_DIR_CAT_LIST = "CVS1/eudicCatList.txt";
    public static final String FILE_DIR_CAT_WORD_LIST = "CVS1/eudicCatWordList.txt";
    public static final String FILE_DIR_NOTE_TEMP = "CVS1/temp";

    public static final String FILE_LOG_PROP = "CVS1/jinlog.properties";

    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UTF8 = "UTF-8";
    public static final String LINE_JOINNER = "||";
    public static final String LINE_SPLITTER = "\\|\\|";
    public static final String WORD_JOINNER = "!@#";
    public enum WriteNoteType {
        APPEND,REPLACE;
    }
}
