package com.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.rpc.services.HelloService;
import com.rpc.services.impl.HelloServiceImpl;

public class RpcTest {
	 
    public static void main(String[] args) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {  
                try {  
                    Server serviceServer = new ServiceCenter(8088);  
                    serviceServer.register(HelloService.class, HelloServiceImpl.class);  
                    serviceServer.start();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }).start();  
    }  
  
}
