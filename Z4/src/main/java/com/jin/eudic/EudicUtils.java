package com.jin.eudic;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EudicUtils {
	public static final String HEADER_NAME_AUTH = "Authorization";
	public static final String HEADER_VALUE_AUTH = "NIS co/2bshIC2EPiKS6zd13L5piJTH3xPI4bujrOW2ytNAsYiivVuaShQ==";
	public static final String HEADER_NAME_CONTENT_TYPE = "Content-Type";
	public static final String HEADER_VALUE_CONTENT_TYPE = "application/json;charset=utf8";
	public static final String USER_NAME_COOKIES = "Cookie";
	public static final String USER_VALUE_COOKIES = "__cfduid=d1c94c633bef9af71e7205da1751ed7fe1600398268; __utma=131758875.1734058640.1600398321.1600398321.1600398321.1; __utmc=131758875; __utmz=131758875.1600398321.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); EudicWebSession=QYNeyJoYXNfb2xkX3Bhc3N3b3JkIjpmYWxzZSwidG9rZW4iOiIrU3JsOHd2QUJvRTNacHl3V0JtRTc1Y1g3SDg9IiwiZXhwaXJlaW4iOjEzMTQwMDAsInVzZXJpZCI6ImFjZTczNDVlLTIzOTYtMTFlYS04M2U4LWQ0NzhlMTc4OWM1NiIsInVzZXJuYW1lIjoid2o1MzloQGhvdG1haWwuY29tIiwiY3JlYXRpb25fZGF0ZSI6IjIwMTktMTItMjBUMTg6MDc6NDVaIiwicm9sZXMiOm51bGwsIm9wZW5pZF90eXBlIjpudWxsLCJvcGVuaWRfZGVzYyI6bnVsbCwicHJvZmlsZSI6eyJuaWNrbmFtZSI6IndqNTM5aCIsImVtYWlsIjoid2o1MzloQGhvdG1haWwuY29tIiwiZ2VuZGVyIjpudWxsLCJwYXNzd29yZCI6bnVsbCwidm9jYWJ1bGFyaWVzIjp7fX0sInJlZGlyZWN0X3VybCI6bnVsbH0%3d; __gads=ID=71d812375b515162:T=1600398498:S=ALNI_MYfKYwWDlrRAVuXBK8CR6Z7mGqWrw; ASP.NET_SessionId=mhckvdg0tsy2ro5fki2llz2y; col_index=2; col_sort=asc";
	public static CloseableHttpResponse sendGet(String url, List<NameValuePair> params) {
		// CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		// 参数
		URI uri = null;
		try {
			// 将参数放入键值对类NameValuePair中,再放入集合中
			if (params == null) {
				params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("language", "en"));
			}
			// 设置uri信息,并将参数集合放入uri;
			// 注:这里也支持一个键值对一个键值对地往里面放setParameter(String key, String value)
			uri = new URIBuilder(url).setParameters(params).build();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建Get请求
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader(HEADER_NAME_AUTH, HEADER_VALUE_AUTH);
		httpGet.setHeader(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE);
		httpGet.setHeader(USER_NAME_COOKIES, USER_VALUE_COOKIES);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					// 设置连接超时时间(单位毫秒)
					.setConnectTimeout(5000)
					// 设置请求超时时间(单位毫秒)
					.setConnectionRequestTimeout(5000)
					// socket读写超时时间(单位毫秒)
					.setSocketTimeout(5000)
					// 设置是否允许重定向(默认为true)
					.setRedirectsEnabled(true).build();

			// 将上面的配置信息 运用到这个Get请求里
			// httpGet.setConfig(requestConfig);

			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CloseableHttpResponse sendGet(String url) {
		return sendGet(url, null);
	}

	public static CloseableHttpResponse sendPost(String url, List<NameValuePair> paramList) {
		// 创建Httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		// 设置2个post参数，一个是scope、一个是q
		// 构造一个form表单式的实体
		CloseableHttpResponse response = null;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramList, "UTF-8");

			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
			// 伪装浏览器
			httpPost.setHeader(USER_NAME_COOKIES, USER_VALUE_COOKIES);
			httpPost.setHeader(HEADER_NAME_CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
			// 执行请求
			response = httpclient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CloseableHttpResponse sendPostJson(String url, String json) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		StringEntity requestEntity = new StringEntity(json, "UTF-8");
		try {
			httpPost.setHeader(USER_NAME_COOKIES, USER_VALUE_COOKIES);
			httpPost.setHeader(HEADER_NAME_AUTH, HEADER_VALUE_AUTH);
			httpPost.setHeader(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE);
			httpPost.setEntity(requestEntity);
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
