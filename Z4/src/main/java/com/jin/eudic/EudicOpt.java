package com.jin.eudic;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;
import java.util.stream.Collectors;

public class EudicOpt {
	public enum ResponseCode {SUCCESS, ERROR}
	public static final String URL_CATEGORY_QUERY = "https://api.frdic.com/api/open/v1/studylist/category";
	public static final String URL_WORD = "https://api.frdic.com/api/open/v1/studylist/words/";
	public static final String URL_CAT_WORD_OPT = "http://my.eudic.net/StudyList/Edit";
	public static final String URL_WORD_LOOK_FOR = "https://dict.eudic.net/dicts/en/";
	public static final String URL_CUSTOMIZE_INFO = "https://dict.eudic.net/dicts/CustomizeInfo";
	public static final String URL_SET_NOTE = "https://dict.eudic.net/Dicts/SetNote";

	public static final String FILE_DIR_CAT_LIST = "CVS1/eudicCatList.txt";
	public static final String FILE_DIR_CAT_WORD_LIST = "CVS1/eudicCatWordList.txt";
	public static final String STR_CRLF = "\r\n";
	public static final String STR_UTF8 = "UTF-8";
	public static final String STR_LINE_JOINNER = "||";
	public static final String STR_LINE_SPLITTER = "\\|\\|";
	public static final String STR_WORD_JOINNER = "!@#";
	public static final String FILE_DIR_NOTE_TEMP = "CVS1/temp";
	private Map<String, String> categoryMap = null;

	private boolean loadAllCatFromWeb = false;
	private boolean loadCategoryMap = false;
	private boolean writeCatListFile = false;
	private boolean writeCatWordListFile = false;

	public EudicOpt() {
	}

	public void init() {
		if (loadCategoryMap) {
			categoryMap = loadAllCatFromWeb ? queryAllCategory() : queryAllCatFromFile();
		}
	}

	public Map<String, String> queryAllCategory() {
		Map<String, String> result = null;
		CloseableHttpResponse response = null;
		try {
			response = EudicUtils.sendGet(URL_CATEGORY_QUERY);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			System.out.println("响应状态为:" + response.getStatusLine());
			if (entity != null) {
				Gson gson = new Gson();
				Map<String, List> map = gson.fromJson(json, Map.class);
				List<Map<String, String>> list = (List) map.get("data");
				if (list != null && list.size() != 0) {
					result = new HashMap<String, String>();
					for (Map<String, String> every : list) {
						result.put(every.get("name"), every.get("id"));
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result != null && !result.isEmpty()) {
			System.out.println("共有 " + result.size() + " 个生词本   -----   " + response.getStatusLine().getStatusCode());
		}

		if (writeCatListFile) {
			outToCatFile(result);
		}
		return result;
	}

	public Map<String, String> queryAllCatFromFile() {
		Map<String, String> result = null;
		File f = new File(FILE_DIR_CAT_LIST);
		try {
			if (f.exists()) {
				List<String> l = FileUtils.readLines(f, STR_UTF8);
				for (String line : l) {
					if (result == null)
						result = new HashMap<String, String>();
					String tempArr[] = line.split(STR_LINE_SPLITTER);
					result.put(tempArr[0], tempArr[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public List<String> findWordsInCategory(String categoryName) {
		return findWordsInCategory(categoryName,0);
	}
	public List<String> findWordsInCategory(String categoryName, int count) {
		List<String> result = null;

		String categoryId = categoryMap.get(categoryName);
		if (StringUtils.isNotEmpty(categoryId)) {
			CloseableHttpResponse response = null;
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("language", "en"));
				params.add(new BasicNameValuePair("category_id", categoryId));
				response = EudicUtils.sendGet(URL_WORD + categoryId);
				if (response == null) {
					return null;
				}
				HttpEntity entity = response.getEntity();
				String json = EntityUtils.toString(entity);
				if (json.contains("Time-out")) {
					return null;
				}
				System.out.println(++count + " 生词本 : " + categoryName + " --- " + response.getStatusLine());
				if (entity != null) {
					Gson gson = new Gson();
					Map<String, List> map = gson.fromJson(json, Map.class);
					List<Map<String, String>> list = (List) map.get("data");
					if (list != null && list.size() != 0) {
						result = new ArrayList<String>();
						for (Map<String, String> every : list) {
							result.add(every.get("word"));
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("找不到生词本 : " + categoryName);
		}

		return result;
	}

	private Map<String, List<String>> catWordMap = null;

	public List<String> findCatsForWord(String word) {
		List<String> result = new ArrayList<String>();
		Set<String> keySet = categoryMap.keySet();
		for (String key : keySet) {
			if (catWordMap == null)
				catWordMap = new Hashtable<String, List<String>>();
			List<String> wordsInCat = findWordsInCategory(key);
			catWordMap.put(key, wordsInCat == null ? new ArrayList<String>() : wordsInCat);
			if (wordsInCat != null && wordsInCat.contains(word)) {
				result.add(key);
			}
			/*
			 * new Thread(new Runnable() { public void run() { List<String> wordsInCate =
			 * findWordsInCategory(key); table.put(key, wordsInCate==null?new
			 * ArrayList<String>():wordsInCate); if(keySet.size() == table.size()) {
			 * System.out.println(table); } } }).start();
			 */
		}
		if (result.isEmpty()) {
			System.out.println("没有生词本里包含 : " + word);
		}
		if (writeCatWordListFile)
			outToCatWordFile();
		return result;
	}

	public List<String> findCatsForWordFromFile(String word) {
		List<String> result = new ArrayList<String>();
		File f = new File(FILE_DIR_CAT_WORD_LIST);
		try {
			if (f.exists()) {
				List<String> l = FileUtils.readLines(f, STR_UTF8);
				for (String line : l) {
					if (line.contains(word)) {
						String tempArr[] = line.split(STR_LINE_SPLITTER);
						result.add(tempArr[0]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 0106A | []
	// 0109B | [eastbound......]
	public void outToCatWordFile() {
		File f = new File(FILE_DIR_CAT_WORD_LIST);
		try {
			boolean createOrDelE = f.exists() ? f.delete() : f.createNewFile();
			if (!catWordMap.isEmpty()) {
				for (Map.Entry<String, List<String>> me : catWordMap.entrySet()) {
					FileUtils.write(f, me.getKey() + STR_LINE_JOINNER + me.getValue() + STR_CRLF, STR_UTF8, true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 0206B | 1579859319
	// 0206A | 1579859315
	public void outToCatFile(Map<String, String> catMap) {
		File f = new File(FILE_DIR_CAT_LIST);
		try {
			boolean createOrDelE = f.exists() ? f.delete() : f.createNewFile();
			if (!catMap.isEmpty()) {
				for (Map.Entry<String, String> me : catMap.entrySet()) {
					FileUtils.write(f, me.getKey() + STR_LINE_JOINNER + me.getValue() + STR_CRLF, STR_UTF8, true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// {"msg":"添加分组成功","bookid":"132453104041475005","result":true} "catename",
	// "gogogo" "oper": "insertcate"
	public String addCategory(String catName) {
		String result = null;
		if (categoryMap.containsKey(catName)) {
			result = "{\"msg\":\" " + catName + " 已经存在\",\"result\":false}";
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
			paramList.add(new BasicNameValuePair("oper", "insertcate"));
			paramList.add(new BasicNameValuePair("catename", catName));
			result = callCommonPost(URL_CAT_WORD_OPT,paramList);
		}
		return result;
	}

	// {"msg":"分组删除成功","result":true} "uuid", "0" "oper", "deletecate"
	public String delCategory(String catName) {
		String result = null;
		if (!categoryMap.containsKey(catName)) {
			result = "{\"msg\":\" " + catName + " 不存在\",\"result\":false}";
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
			paramList.add(new BasicNameValuePair("oper", "deletecate"));
			paramList.add(new BasicNameValuePair("uuid", categoryMap.get(catName)));
			result = callCommonPost(URL_CAT_WORD_OPT,paramList);
		}
		return result;
	}

	// {"msg":"分组名称更改成功","result":true} "uuid", "0" "oper", "changecatename"
	// "catename", "temp（默认）Jin"
	public String updCategory(String oldName, String newName) {
		String result = null;
		if (!categoryMap.containsKey(oldName)) {
			result = "{\"msg\":\" " + oldName + " 不存在\",\"result\":false}";
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
			paramList.add(new BasicNameValuePair("oper", "changecatename"));
			paramList.add(new BasicNameValuePair("uuid", categoryMap.get(oldName)));
			paramList.add(new BasicNameValuePair("catename", newName));
			result = callCommonPost(URL_CAT_WORD_OPT,paramList);
		}
		return result;
	}

	private String callCommonPost(String url,List<NameValuePair> paramList) {
		String result = null;
		CloseableHttpResponse response = EudicUtils.sendPost(url, paramList);
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				result = EntityUtils.toString(response.getEntity(), STR_UTF8);
			}
			if (statusCode == 200 && StringUtils.isEmpty(result)) {
				result = response.getStatusLine().toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String delWordFromCat(String catName, List<String> wordList) {
		String result = null;
		if (!categoryMap.containsKey(catName)) {
			result = "{\"msg\":\" " + catName + " 不存在\",\"result\":false}";
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
			paramList.add(new BasicNameValuePair("catename", catName));
			paramList.add(new BasicNameValuePair("oldcateid", categoryMap.get(catName)));
			paramList.add(new BasicNameValuePair("oper", "del"));
			String wordsStr = String.join(STR_WORD_JOINNER,
					wordList.stream().map(String::valueOf).collect(Collectors.toList()));
			paramList.add(new BasicNameValuePair("uuid", wordsStr));
			result = callCommonPost(URL_CAT_WORD_OPT,paramList);
		}
		return result;
	}

	public String moveWordFromCat(String oldCatName, String newCatName, List<String> wordList) {
		String result = null;
		if (!categoryMap.containsKey(oldCatName)) {
			result = "{\"msg\":\" " + oldCatName + " 不存在\",\"result\":false}";
		} else if (!categoryMap.containsKey(newCatName)) {
			result = "{\"msg\":\" " + newCatName + " 不存在\",\"result\":false}";
		} else {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
			paramList.add(new BasicNameValuePair("catename", categoryMap.get(newCatName)));
			paramList.add(new BasicNameValuePair("oldcateid", categoryMap.get(oldCatName)));
			paramList.add(new BasicNameValuePair("oper", "moveword"));
			String wordsStr = String.join(STR_WORD_JOINNER,
					wordList.stream().map(String::valueOf).collect(Collectors.toList()));
			paramList.add(new BasicNameValuePair("uuid", wordsStr));
			result = callCommonPost(URL_CAT_WORD_OPT,paramList);
		}
		return result;
	}

	public String addWordToCat(String catName, List<String> wordList) {
		String result = null;
		if (!categoryMap.containsKey(catName)) {
			result = "{\"msg\":\" " + catName + " 不存在\",\"result\":false}";
		} else {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("id", categoryMap.get(catName));
			jsonMap.put("language", "en");
			jsonMap.put("words", (List<String>) wordList);
			Gson gson = new Gson();
			String json = gson.toJson(jsonMap);
			CloseableHttpResponse response = EudicUtils.sendPostJson(URL_WORD, json);
			try {
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 201) {
					result = EntityUtils.toString(response.getEntity(), STR_UTF8);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String findNoteByWord(String word) {
		String result = StringUtils.EMPTY;
		try {
			String pageStatus = findPageStatus(word.contains(" ")?word.replace(" ", "%20"):word);
			if (StringUtils.isNotEmpty(pageStatus)) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("status", pageStatus));
				CloseableHttpResponse response = EudicUtils.sendGet(URL_CUSTOMIZE_INFO, params);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					String json = EntityUtils.toString(response.getEntity(), STR_UTF8);
					Gson gson = new Gson();
					List<String> list = gson.fromJson(json, List.class);
					if(list != null && !list.isEmpty() && StringUtils.isNotEmpty(list.get(3))) {
						result = StringEscapeUtils.unescapeHtml4(list.get(3));
						//System.out.println(result);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String findPageStatus(String word) throws UnsupportedOperationException, IOException {
		String result = StringUtils.EMPTY;
		CloseableHttpResponse response = EudicUtils.sendGet(URL_WORD_LOOK_FOR + word);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			//reader.skip(6000);
			while ((line = reader.readLine()) != null) {
				if (line.contains("id=\"page-status\"")) {
					int s = line.indexOf("QYN");
					int e = line.lastIndexOf("\" lang=");
					result = line.substring(s, e);
					IOUtils.close(response.getEntity().getContent());
					break;
				}
			}
		}
		response.close();
		//System.out.println(word+" --- pageStatus : "+result);
		return result;
	}
	
	public Map<String,String> findWordsAndNoteByCat(String catName) {
		Map<String,String> resultMap = new HashMap<String,String>();
		if (!categoryMap.containsKey(catName)) {
			resultMap.put("msg", catName + " 不存在");
			resultMap.put("result", "false");
		} else {
			List<String> wordList = findWordsInCategory(catName);
			for(String word : wordList) {
				String note = findNoteByWord(word);
				resultMap.put(word, note);
			}
		}
		return resultMap;
	}
	
	public String updateWordNote(String word, String note) {
		String result = null;
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("note", note);
		jsonMap.put("lang", "en");
		jsonMap.put("word", word);
		Gson gson = new Gson();
		String json = gson.toJson(jsonMap);
		CloseableHttpResponse response = EudicUtils.sendPostJson(URL_SET_NOTE, json);
		try {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				result = EntityUtils.toString(response.getEntity(), STR_UTF8);
			}
			if (statusCode == 200 && StringUtils.isEmpty(result)) {
				result = response.getStatusLine().toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	//将word和note从temp文件里面加载到Map, key是word, value是note
	public Map<String,String> loadWordNoteFromFile() {
		Map<String,String> resultMap = new HashMap<String,String>();
		File f = new File(FILE_DIR_NOTE_TEMP);
		try {
			if (f.exists()) {
				List<String> list = FileUtils.readLines(f, STR_UTF8);
				
				String word = null,note=null;
				for (String line : list) {
					if(line.contains(STR_LINE_JOINNER)) {
						if(word != null) word = null;
						if(note != null) note = null;
						String tempArr[] = line.split(STR_LINE_SPLITTER);
						word = tempArr[0];
						note = tempArr.length >1?tempArr[1]+STR_CRLF : StringUtils.EMPTY;
					} else {
						note +=line+STR_CRLF;
						resultMap.put(word, note);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public enum WriteNoteType {
		APPEND,REPLACE;
	}

	//加载temp文件的word和note,更新或替换note,然后把word加到cat里
	public void addWordToCateAndImportNote(String catName, WriteNoteType wnt) {
		Map<String,String> map = loadWordNoteFromFile();
		List<String> wordList = new LinkedList<String>();
		int n=0;
		for (Map.Entry<String,String> me : map.entrySet()) {
			String word = null;
			if((word = me.getKey()) != null) {
				wordList.add(word);
				String noteToImport = null;
				switch(wnt) {
					case APPEND:
						String oriNote = findNoteByWord(word);
						String double_crlf = StringUtils.isEmpty(oriNote)?"":EudicOpt.STR_CRLF+EudicOpt.STR_CRLF;
						noteToImport = oriNote+double_crlf+me.getValue();
						break;
					case REPLACE:
						noteToImport = me.getValue();
						break;
					default:
				}
				String updateNoteResult = updateWordNote(me.getKey(), noteToImport);
				System.out.println(++n+" -- "+me.getKey()+" -- "+updateNoteResult);
			}
		}
		addWordToCat(catName, wordList);
	}

	public void addWordToCateAndImportNote(String catName) {
		addWordToCateAndImportNote(catName, WriteNoteType.APPEND);
	}

	public void setLoadAllCatFromWeb(boolean loadAllCatFromWeb) {
		this.loadAllCatFromWeb = loadAllCatFromWeb;
	}
	public void setLoadCategoryMap(boolean loadCategoryMap) {
		this.loadCategoryMap = loadCategoryMap;
	}
	public void setWriteCatListFile(boolean writeCatListFile) {
		this.writeCatListFile = writeCatListFile;
	}
	public void setWriteCatWordListFile(boolean writeCatWordListFile) {
		this.writeCatWordListFile = writeCatWordListFile;
	}
	public Map<String, String> getCategoryMap() {
		return categoryMap;
	}
}