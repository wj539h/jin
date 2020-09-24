package com.net;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class NioSocketServer {
    public static void main(String[] args) throws Exception {
	LinkedList<SocketChannel>  clients = new LinkedList<>();
	
	ServerSocketChannel ss = ServerSocketChannel.open();
	ss.bind(new InetSocketAddress(9090));
	ss.configureBlocking(false); //重点 OS NONBLOCKING!!!
	//ss.setOption(StandardSocketOptions.TCP_NODELAY, false);
	
	while(true) {
	    Thread.sleep(5000);
	    SocketChannel client = ss.accept();//不会阻塞, 程序往下走, Kernal返回 -1, Java返回Client为null
	    if(client == null) {
		System.out.println("client is null............");
	    } else {
		client.configureBlocking(false); //重点   socket (服务端的listen socket连接请求3次握手)
		System.out.println("A clinet connection is coming, IP : "+client.socket().getInetAddress().getHostAddress()+
			" port : "+client.socket().getPort());
		clients.add(client); //将过来接收到的socket放到一个list里, 下面处理
	    }
	    
	    ByteBuffer buffer = ByteBuffer.allocateDirect(4096);
	    
	    //遍历处理来的每一个client
	    for(SocketChannel c : clients) {
		int num = c.read(buffer); //由于上面设置了client的非阻塞client.configureBlocking(false); 不会阻塞
		if(num > 0) {
		    buffer.flip();
		    byte[] bArr = new byte[buffer.limit()];
		    buffer.get(bArr);
		    
		    String str = new String(bArr);
		    System.out.println("IP : "+c.socket().getInetAddress().getHostAddress()+
			" port : "+c.socket().getPort()+ " ====== "+str);
		    buffer.clear();
		}
	    }
	}
    }
}
