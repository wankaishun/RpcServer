package com.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rpc.handler.InvokerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServiceCenter implements Server {


	private static ExecutorService   executor= Executors.newFixedThreadPool(Runtime.getRuntime()  
			.availableProcessors());  


	private static final ConcurrentHashMap<String, Object> serviceRegistry = new ConcurrentHashMap<String, Object>();  


	private static boolean  isRunning = false;  


	private static int  port;  


	public ServiceCenter(int port) {  
		this.port = port;  
	}  


	public void stop() {  
		isRunning = false;  
		executor.shutdown();  
	}  


	public void start() throws IOException {  
		final InvokerHandler invokerHandler=new InvokerHandler();
		invokerHandler.setClassMap(serviceRegistry);
		EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap serverBootstrap = new ServerBootstrap().group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)  
                    .localAddress(port).childHandler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
    						//超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                            ch.pipeline().addLast(new ReadTimeoutHandler(5)); 
                            ch.pipeline().addLast("handler",invokerHandler);
                        }  
                    }).option(ChannelOption.SO_BACKLOG, 128)       
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
            ChannelFuture future = serverBootstrap.bind(port).sync();      
            System.out.println("Server start listen at " + port );    
            future.channel().closeFuture().sync();    
        } catch (Exception e) {  
             bossGroup.shutdownGracefully();    
             workerGroup.shutdownGracefully();  
        } 
	}  


	public void register(Class serviceInterface, Class impl) {  
		serviceRegistry.put(serviceInterface.getName(), impl);  
	}  


	public boolean isRunning() {  
		return isRunning;  
	}  


	public int getPort() {  
		return port;  
	}  


	private static class ServiceTask implements Runnable {  
		Socket clent = null;  


		public ServiceTask(Socket client) {  
			this.clent = client;  
		}  


		public void run() {/*  
			ObjectInputStream input = null;  
			ObjectOutputStream output = null;  
			try {  
				// 2.将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果  
				input = new ObjectInputStream(clent.getInputStream());  
				String serviceName = input.readUTF();  
				String methodName = input.readUTF();  
				Class<?>[] parameterTypes = (Class<?>[]) input.readObject();  
				Object[] arguments = (Object[]) input.readObject();  
				Class serviceClass = serviceRegistry.get(serviceName);  
				if (serviceClass == null) {  
					throw new ClassNotFoundException(serviceName + " not found");  
				}  
				Method method = serviceClass.getMethod(methodName, parameterTypes);  
				Object result = method.invoke(serviceClass.newInstance(), arguments);  


				// 3.将执行结果反序列化，通过socket发送给客户端  
				output = new ObjectOutputStream(clent.getOutputStream());  
				output.writeObject(result);  
			} catch (Exception e) {  
				e.printStackTrace();  
			} finally {  
				if (output != null) {  
					try {  
						output.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				}  
				if (input != null) {  
					try {  
						input.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				}  
				if (clent != null) {  
					try {  
						clent.close();  
					} catch (IOException e) {  
						e.printStackTrace();  
					}  
				}  
			}  

*/
			
			 
	         
		}  
	}  




}
