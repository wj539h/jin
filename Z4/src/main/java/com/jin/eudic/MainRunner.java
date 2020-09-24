package com.jin.eudic;

import java.util.Map;

public class MainRunner {
    public static void main(String[] args) {
        EudicOpt eo = new EudicOpt();
        eo.setLoadCategoryMap(true);eo.setLoadAllCatFromWeb(false);
        eo.setWriteCatListFile(false);eo.setWriteCatWordListFile(false);
		eo.init();
		String note = eo.findNoteByWord("slightly");
		System.out.println(eo.updateWordNote("slightly", note));
		//System.out.println(eo.getCategoryMap());
        //eo.queryAllCategory();
        //System.out.println(eo.findCatsForWordFromFile("striking"));
		/*
		 * List<String> wordList = new ArrayList<String>();
		 * wordList.add("fuck");wordList.add("shit"); eo.moveWordFromCat("0301B",
		 * "temp（默认）Jin", wordList);
		 */
        //System.out.println(eo.delWordFromCat("temp（默认）Jin", wordList));
        //eo.queryAllCategory(true);
        //System.out.println(eo.findCategoriesForWord("striking", true));
        //System.out.println(eo.delCategory("0301TTT"));
        //eo.outToFile("ffffff");
    }
}
