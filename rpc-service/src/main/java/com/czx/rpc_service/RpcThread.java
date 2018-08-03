package com.czx.rpc_service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcThread implements Runnable{

	private Socket socket;
	
	public RpcThread(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run() {
		// TODO Auto-generated method stub


		ObjectInputStream obs = null;
		ObjectOutputStream ots = null;
		try {
			//输入输出流
			//输入流，客户端的信息
			obs = new ObjectInputStream(socket.getInputStream());
			RpcObject ob = (RpcObject)obs.readObject();
			System.out.println("服务端收到的方法信息："+ob.getMethodName());
			System.out.println("服务端收到的参数信息："+ob.getArgs().toString());
			//将对象实例化并调用对应方法返回值
			Object obj = toInvoke(ob);
			
			System.out.println("***************************");
			System.out.println("***************************");
			
			System.out.println("发送给客户端的信息："+obj.toString());
			//将数据输出到输出流中
			ots = new ObjectOutputStream(socket.getOutputStream());
			ots.writeObject(obj);
			ots.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// TODO: handle finally clause
			try {
				obs.close();
				ots.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	private static Object toInvoke(RpcObject ob) {
		// TODO Auto-generated method stub
		//将rpcobject对象的className进行转换
		String finallyClassName = toChangeClassName(ob.getC().getName());
		Object oo = null;
		try {
			Class<?>  clazz= Class.forName(finallyClassName);
			
			//遍历参数
			Class[] cs = new Class[ob.getArgs().length];
			for (int i = 0; i < ob.getArgs().length; i++) {
				Object arg = ob.getArgs()[i];
				cs[i] = arg.getClass();
			}
			
			//调用指定方法并将参数传入
			Method me = clazz.getMethod(ob.getMethodName(), cs);
			oo = me.invoke(clazz.newInstance(), ob.getArgs());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("类未找到");
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("找不到指定方法");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return oo;
	}
	
	private static String toChangeClassName(String name) {
		// TODO Auto-generated method stub
		int indexa = name.lastIndexOf(".");
		String c = name.subSequence(0, indexa+1) + name.substring(indexa+2);
		return c+"Impl";
	}
}
