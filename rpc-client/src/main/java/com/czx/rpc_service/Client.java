package com.czx.rpc_service;

import java.lang.reflect.Proxy;


public class Client {

	public static void main(String[] args) {
		
		IHello hello = ProxyFactory.create(IHello.class);
		System.out.println(hello.sayHello("wzj"));
		 
		ProxyFactory  a  = new ProxyFactory();
		a.create(IHello.class);
	
	}
}
