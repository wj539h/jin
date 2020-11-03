package com.jin.eudic;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.jin.eudic.EudicConst.*;
import static com.jin.Const.*;

public class EudicReadOpt extends EudicOpt{
    //查询cat下面所有单词和note
    public Map<String,String> findWordsAndNoteByCat(String catName) {
        Map<String,String> resultMap = new ConcurrentSkipListMap<String,String>();
        if (!categoryMap.containsKey(catName)) {
            resultMap.put("msg", catName + " 不存在");
            resultMap.put("result", "false");
        } else {
            List<String> wordList = findWordsInCategory(catName);
            if(wordList == null || wordList.isEmpty()) {
                resultMap.put("msg", catName + "中还没有单词");
                resultMap.put("result", "false");
            } else {
                for(String word : wordList) {
                    String note = findNoteByWord(word);
                    //note=  note.replace(HTML_BR, CRLF);
                    resultMap.put(word, note);
                }
            }
        }
        return resultMap;
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

    //查看单词所在的cats
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
                List<String> l = FileUtils.readLines(f, UTF8);
                for (String line : l) {
                    if (line.contains(word)) {
                        String tempArr[] = line.split(LINE_SPLITTER);
                        result.add(tempArr[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
