package com.rpc.services.impl;

import com.rpc.services.HelloService;

public class HelloServiceImpl implements HelloService {

	 public String sayHi(String name) {  
	        return "Hi, " + name;  
	    }  
}
