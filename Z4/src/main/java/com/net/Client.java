package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
	Socket socket = new Socket();
	try {
	    InetSocketAddress inetSocketAddress = new InetSocketAddress("172.17.131.26", 8090);
	    socket.connect(inetSocketAddress);

	    socket.getOutputStream().write("abc\r\n".getBytes());
	    socket.getOutputStream().write("jin\r\n".getBytes());
	    socket.getOutputStream().write("exit\r\n".getBytes());

	    InputStream is = socket.getInputStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    BufferedReader br = new BufferedReader(isr);
	    String str = null;
	    while ((str = br.readLine()) != null) {
		System.out.println("Client : " + str);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if(!socket.isInputShutdown()) 
		    socket.shutdownInput();
		if(!socket.isOutputShutdown()) 
		    socket.shutdownOutput();
		if(!socket.isClosed()) 
		    socket.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
    /*public static void main(String[] args) throws Exception{
        URL serverUrl = new URL("https://www.baidu.com");
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-type", "application/json");
        //必须设置false，否则会自动redirect到重定向后的地址
        conn.setInstanceFollowRedirects(false);
        conn.connect();
        String result = getReturn(conn);
        System.out.println(result);
        if(conn != null) {
            conn.disconnect();
        }
    }
    
    请求url获取返回的内容
    public static String getReturn(HttpURLConnection connection) throws IOException{
        StringBuffer buffer = new StringBuffer();
        //将返回的输入流转换成字符串
        try(InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            String result = buffer.toString();
            return result;
        }
    }*/
}
