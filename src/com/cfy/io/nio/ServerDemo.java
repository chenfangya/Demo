package com.cfy.io.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author created by ChenFangYa
 * @date 2020年6月11日---下午1:41:13
 * @action
 */
public class ServerDemo {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		InputStream in = null;
		try {
			serverSocket = new ServerSocket(8080);
			int recvMsgSize = 0;
			byte[] recvBuf = new byte[1024];
			while (true) {
				Socket clntSocket = serverSocket.accept();
				SocketAddress clientAddress = clntSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);
				in = clntSocket.getInputStream();
				while ((recvMsgSize = in.read(recvBuf)) != -1) {
					byte[] temp = new byte[recvMsgSize];
					System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
					System.out.println(new String(temp));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
