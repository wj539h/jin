package com.jin.eudic;

public class MainRunner {
	public static void main(String[] args) throws Exception {
		EudicOpt eo = new EudicOpt();
		eo.setLoadCategoryMap(true);
		eo.setLoadAllCatFromWeb(false);
		eo.setWriteCatListFile(false);
		eo.setWriteCatWordListFile(false);
		eo.init();
		//查询某一个单词的note
		/*String word = "dog";
		String note = eo.findNoteByWord(word);
		System.out.println(note);
		
		//更新一个单词的note
		note+="hahaha";
		System.out.println(eo.updateWordNote(word, note));*/
		
		
		//查询Category里面的词和note
		/*String catName = "0213B";
		Map<String,String> map = eo.findWordsAndNoteByCat(catName);
		for (Map.Entry<String, String> me : map.entrySet()) {
			System.out.println(me.getKey()+EudicOpt.STR_LINE_JOINNER+me.getValue());
		}*/
		
		
		
		//从temp文件加载单词和note到map,然后导入到eudic里
		String catName = "0301B";
		eo.addWordToCateAndImportNote(catName, EudicOpt.WriteNoteType.REPLACE);

		
		//列出单词所在的Category
		//System.out.println(eo.findCatsForWord("shift"));
		
		
		//System.out.println(eo.updateWordNote("slightly", note));
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




