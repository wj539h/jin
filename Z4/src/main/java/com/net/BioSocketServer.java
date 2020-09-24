package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BioSocketServer {
    public static void main(String[] args) {
	try {
	    final ServerSocket ss = new ServerSocket(8090);
	    while (true) {
		final Socket s = ss.accept();
		System.out.println("A clinet connection is coming, IP : "+s.getInetAddress().getHostAddress()+" port : "+s.getPort()+" hostName : "+s.getLocalAddress().getCanonicalHostName());
		new Thread(new Runnable() {
		    
		    @Override
		    public void run() {
			try {
			    InputStream is = s.getInputStream();
			    InputStreamReader isr = new InputStreamReader(is); 
			    BufferedReader br = new BufferedReader(isr);
			    String str = null;
			    while((str = br.readLine()) != null) {
				System.out.println(str);
				OutputStream os = s.getOutputStream();
				os.write( ("Server is writing to clinet : "+str+"\r\n").getBytes());
				if(str.equals("exit") || str.equals("quit")) {
				    s.close();
				    System.out.println("Server is closing Client Socket");
				    break;
				}
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }

		}).start();
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
