package com.rpc.handler;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import com.rpc.pojo.ClassInfo;
import com.rpc.services.impl.HelloServiceImpl;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class InvokerHandler extends ChannelHandlerAdapter {  
    private  ConcurrentHashMap<String, Object> classMap;  
    public void setClassMap(ConcurrentHashMap<String, Object> classMap) {
		this.classMap = classMap;
	}
	@Override    
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {    
        ClassInfo classInfo = (ClassInfo)msg;  
        Object claszz = null;  
                claszz = ((Class) classMap.get(classInfo.getClassName())).newInstance();  
                classMap.put(classInfo.getClassName(), claszz);  
        Method method = claszz.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());    
        Object result = method.invoke(claszz, classInfo.getObjects());   
        ctx.write(result);  
        ctx.flush();    
        ctx.close();  
    }    
    @Override    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {    
         cause.printStackTrace();    
         ctx.close();    
    }    
  
}  