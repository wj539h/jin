package com.jin.eudic;

import com.google.gson.Gson;
import com.jin.JinLog;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.jin.eudic.EudicConst.*;

public class EudicOpt {
	private final Logger logger = JinLog.getLogger(EudicOpt.class.getName());
	protected Map<String, String> categoryMap = null;

	protected boolean loadAllCatFromWeb = false;

	protected boolean writeCatListFile = false;
	protected boolean writeCatWordListFile = false;

	public EudicOpt() {
		logger.setLevel(Level.SEVERE);
	}

	protected void init() {
		categoryMap = loadAllCatFromWeb ? queryAllCategory() : queryAllCatFromFile();
	}

	protected Map<String, String> queryAllCategory() {
		Map<String, String> result = null;
		CloseableHttpResponse response = null;
		try {
			response = EudicUtils.sendGet(EudicConst.URL_CATEGORY_QUERY);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			logger.info("响应状态为:" + response.getStatusLine());
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
			logger.info("共有 " + result.size() + " 个生词本   -----   " + response.getStatusLine().getStatusCode());
		}

		if (writeCatListFile) {
			outToCatFile(result);
		}
		return result;
	}

	protected Map<String, String> queryAllCatFromFile() {
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
				logger.info(result.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 0206B | 1579859319
	// 0206A | 1579859315
	protected void outToCatFile(Map<String, String> catMap) {
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

	// 0106A | []
	// 0109B | [eastbound......]
	protected void outToCatWordFile() {
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



	


	protected Map<String, List<String>> catWordMap = null;

	protected String callCommonPost(String url,List<NameValuePair> paramList) {
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




	


	








	//将word和note从temp文件里面加载到Map, key是word, value是note
	protected Map<String,String> loadWordNoteFromFile() {
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

	//查询单词的note
	protected String findNoteByWord(String word) {
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
	//分析word页面的html, 返回QYN的值
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

	public void setLoadAllCatFromWeb(boolean loadAllCatFromWeb) {
		this.loadAllCatFromWeb = loadAllCatFromWeb;
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