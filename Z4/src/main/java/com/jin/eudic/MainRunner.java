package com.jin.eudic;

import com.jin.Const;

public class MainRunner {
	public static void main(String[] args) throws Exception {
		EudicReadOpt eo = new EudicReadOpt();
		//EudicWriteOpt eo = new EudicWriteOpt();
		//eo.setLoadAllCatFromWeb(false);
		//eo.setWriteCatListFile(true);
		//eo.setWriteCatWordListFile(true);
		eo.init();
		//查询某一个单词的note
		String word = "practical";
		String note = eo.findNoteByWord(word);
		Const.pln(note);
		//更新一个单词的note
		/*note+="hahaha";
		Const.pln(eo.updateWordNote(word, note));*/
		
		
		//查询Category里面的词和note
		/*String catName = "0213B";
		Map<String,String> map = eo.findWordsAndNoteByCat(catName);
		for (Map.Entry<String, String> me : map.entrySet()) {
			System.out.println(me.getKey()+EudicConst.STR_LINE_JOINNER+me.getValue());
		}*/
		
		
		
		//从temp文件加载单词和note到map,然后导入到eudic里
		/*String catName = "0301A";
		eo.addWordToCateAndImportNote(catName, EudicConst.WriteNoteType.REPLACE);*/

		
		//列出单词所在的Category
		//Const.pln(eo.findCatsForWord("shift"));
		
		
		//Const.pln(eo.updateWordNote("slightly", note));
		//Const.pln(eo.getCategoryMap());
        //eo.queryAllCategory();
        //Const.pln(eo.findCatsForWordFromFile("striking"));
		/*
		 * List<String> wordList = new ArrayList<String>();
		 * wordList.add("fuck");wordList.add("shit"); eo.moveWordFromCat("0301B",
		 * "temp（默认）Jin", wordList);
		 */
        //Const.pln(eo.delWordFromCat("temp（默认）Jin", wordList));
        //eo.queryAllCategory(true);
        //Const.pln(eo.findCategoriesForWord("striking", true));
        //Const.pln(eo.delCategory("0301TTT"));
        //eo.outToFile("ffffff");
    }
}




