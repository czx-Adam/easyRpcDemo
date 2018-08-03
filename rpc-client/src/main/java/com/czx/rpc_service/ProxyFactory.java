package com.czx.rpc_service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyFactory {

	public static <T> T create(Class<T> c) {
		 InvocationHandler handler = new ProxyRpc(c);
		
		return (T) Proxy.newProxyInstance(c.getClassLoader(),
              new Class[] {c },
              handler);
	}
}
