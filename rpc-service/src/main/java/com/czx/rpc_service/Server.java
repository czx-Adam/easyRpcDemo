package com.czx.rpc_service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class Server 
{
	
	public static void main(String[] args) {
		
		ServerSocket server;
		try {
			server = new ServerSocket(8888);
			System.out.println(server.getLocalPort()+"服务端已开启。。。。。。。");
			
			System.out.println("***************************");
			System.out.println("***************************");
			
			while(true) {
				//获取连接的socket
				Socket socket = server.accept();
				System.out.println("连接中。。。。。。。。");
				RpcThread rt = new RpcThread(socket);
				new Thread(rt).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
