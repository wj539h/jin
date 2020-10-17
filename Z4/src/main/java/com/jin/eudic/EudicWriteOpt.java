package com.jin.eudic;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.jin.eudic.EudicConst.*;

public class EudicWriteOpt extends EudicOpt {

    //完全replace一个word的note
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
                result = EntityUtils.toString(response.getEntity(), UTF8);
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

    //把word添加入cat
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
                    result = EntityUtils.toString(response.getEntity(), UTF8);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //把单词从一个cat中删除
    public String delWordFromCat(String catName, List<String> wordList) {
        String result = null;
        if (!categoryMap.containsKey(catName)) {
            result = "{\"msg\":\" " + catName + " 不存在\",\"result\":false}";
        } else {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>(0);
            paramList.add(new BasicNameValuePair("catename", catName));
            paramList.add(new BasicNameValuePair("oldcateid", categoryMap.get(catName)));
            paramList.add(new BasicNameValuePair("oper", "del"));
            String wordsStr = String.join(WORD_JOINNER,
                    wordList.stream().map(String::valueOf).collect(Collectors.toList()));
            paramList.add(new BasicNameValuePair("uuid", wordsStr));
            result = callCommonPost(URL_CAT_WORD_OPT,paramList);
        }
        return result;
    }

    //把单词从一个cat移到另一个cat
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
            String wordsStr = String.join(WORD_JOINNER,
                    wordList.stream().map(String::valueOf).collect(Collectors.toList()));
            paramList.add(new BasicNameValuePair("uuid", wordsStr));
            result = callCommonPost(URL_CAT_WORD_OPT,paramList);
        }
        return result;
    }

    public void addWordToCateAndImportNote(String catName) {addWordToCateAndImportNote(catName, WriteNoteType.APPEND);}
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
                        String double_crlf = StringUtils.isEmpty(oriNote)?"": CRLF + CRLF;
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

    /*-------------------------------------------------------------------------------------------------*/

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

}
