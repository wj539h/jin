package com.jin.eudic;
import static com.jin.Const.*;
import java.util.Map;

public class MainRunner {

	public static void main(String[] args) throws Exception {
		EudicReadOpt eor = new EudicReadOpt();
		EudicWriteOpt eow = new EudicWriteOpt();
		//eor.setLoadAllCatFromWeb(true);
		//eor.setWriteCatListFile(true);
		//eor.setWriteCatWordListFile(true);
		eor.init();
		eow.setCategoryMap(eor.categoryMap);
		//查询某一个单词的note
		/*String word = "practical";
		String note = eor.findNoteByWord(word);
		note = note.replace(CR, CRLF);
		Const.pln(note);*/

		//更新一个单词的note
		/*note+="hahaha";
		Const.pln(eow.updateWordNote(word, note));*/
		
		
		//查询Category里面的词和note
		String catName = "0205A";
		Map<String,String> map = eor.findWordsAndNoteByCat(catName);
		for (Map.Entry<String, String> me : map.entrySet()) {
			System.out.println(me.getKey()+" - "+me.getValue()+CRLF);
		}
		

		//从temp文件加载单词list,然后导入到eudic里
		/*String catName = "0302A";
		List<String> wordList = eow.loadWordFromFile();
		eow.addWordToCat(catName, wordList);*/

		//从temp文件加载单词和note到map,然后导入到eudic里
		/*String catName = "0302A";
		eow.addWordToCateAndImportNote(catName);//, EudicConst.WriteNoteType.REPLACE
		*/		
		//列出单词所在的Category
		//Const.pln(eor.findCatsForWord("commitment"));
		
		
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




