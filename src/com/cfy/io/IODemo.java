package com.cfy.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.junit.Test;

/**
 * @author created by ChenFangYa
 * @date 2020年1月20日---下午2:50:55
 * @action
 */
public class IODemo {

	@Test
	public void test() throws IOException {
		// TODO 服务端处理客户端连接请求
	    ServerSocket serverSocket = new ServerSocket(3333);

	    // 接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理
	    new Thread(() -> {
	      while (true) {
	        try {
	          // 阻塞方法获取新的连接
	          Socket socket = serverSocket.accept();

	          // 每一个新的连接都创建一个线程，负责读取数据
	          new Thread(() -> {
	            try {
	              int len;
	              byte[] data = new byte[1024];
	              InputStream inputStream = socket.getInputStream();
	              // 按字节流方式读取数据
	              while ((len = inputStream.read(data)) != -1) {
	                System.out.println(new String(data, 0, len));
	              }
	            } catch (IOException e) {
	            }
	          }).start();

	        } catch (IOException e) {
	        }

	      }
	    }).start();
	}
	@Test
	public void test1() throws IOException {
		// TODO 创建多个线程，模拟多个客户端连接服务端
	    new Thread(() -> {
	      try {
	        Socket socket = new Socket("127.0.0.1", 3333);
	        while (true) {
	          try {
	            socket.getOutputStream().write((new Date() + ": hello world").getBytes());
	            Thread.sleep(2000);
	          } catch (Exception e) {
	          }
	        }
	      } catch (IOException e) {
	      }
	    }).start();
	}
}
