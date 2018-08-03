package com.czx.rpc_service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;


public class ProxyRpc implements InvocationHandler{

	private Class c;
	
	public ProxyRpc(Class c) {
		super();
		this.c = c;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		//返回值
		Object obj = null;
		//连接socket
		Socket socket = new Socket("localhost", 8888);
		
		//打印连接的socket的信息
		System.out.println("socket连接是否成功"+socket.isConnected());
		System.out.println("socket的端口:"+socket.getPort());
		
		// 组装为一个保留了要调用的类，方法名及参数的对象，然后序列化之后传给远程
		RpcObject rpcObject = new RpcObject(c, method.getName(), args);
		
		//传输数据
		ObjectOutputStream oos = null;
		//接收数据
		ObjectInputStream ois = null;
		
		System.out.println("***************************");
		
		//socket传输数据到服务端
		System.out.println("传输到服务端的数据："+rpcObject.toString());
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(rpcObject);
		oos.flush();
		
		System.out.println("***************************");
		//接收服务端传递过来的数据
		ois = new ObjectInputStream(socket.getInputStream());
		obj = ois.readObject();
		System.out.println("接收到的数据："+obj.toString());
		oos.close();
		ois.close();
		
		return obj;
	}

}
