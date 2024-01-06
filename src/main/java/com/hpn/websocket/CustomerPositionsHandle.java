package com.hpn.websocket;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.hpn.service.cr.CustomerPositionServiceI;
@ServerEndpoint("/websocket/chat")
public class CustomerPositionsHandle {
	@Resource
	CustomerPositionServiceI service;
	 /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
    	// 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
    	CustomerPositionThread thread = new CustomerPositionThread(session,message);
        thread.start();
    }
    
    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}
