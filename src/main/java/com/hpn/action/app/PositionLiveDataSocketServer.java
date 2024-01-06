package com.hpn.action.app;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;
import com.hpn.model.cr.CustomerPositionPO;

import zone.framework.util.base.ConfigUtil;

public class PositionLiveDataSocketServer{

	public static void main(String[] args) {
		startService();
	}

	private static void startService() {
		try {
			// ServerSocket
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(60301);
			System.out.println("端口号 60301--");
			
			Class.forName(name);//指定连接类型  
	        conn = DriverManager.getConnection(url, user, password);//获取连接  
			while (true) {
				Socket socket = serverSocket.accept(); //等待客户端输入
				System.out.println("客户端" + socket);
				
				startReader(socket);
				//startWriter(socket);
			}
			 
		} catch (Exception e) {
			try {
				conn.close();
				pst.close(); 
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
	         
			e.printStackTrace();
		}
	}

	/** 
	 * 
	 */
	private static void startReader(final Socket socket) {

		new Thread(){
			@Override
			public void run() {
				DataInputStream reader;
				try {
					//
					reader = new DataInputStream( socket.getInputStream());
					while (true) {
						String position = reader.readUTF();
						System.out.println("客户端输入值为：" + position);
						CustomerPositionPO cusPos = (CustomerPositionPO)JSON.parse(position);
						cusPos.setAzimuth(60);
						cusPos.setLatitude(116);
						cusPos.setLongitude(49);
						StringBuilder sqlBuilder = new StringBuilder("insert into hpn_customerPosition")
								.append("(`id`,`operater`, `deleteFlag`, `createDatetime`, `updateDatetime`, ")
								.append("`MACCode`, `latitude`, `longitude`, `azimuth`, `channelType`)VALUES(")
								.append(cusPos.getId()).append("','")
								.append(cusPos.getOperater()).append("','")
								.append(cusPos.getDeleteFlag()).append("',")
								.append(cusPos.getCreateDatetime()).append(",")
								.append(cusPos.getUpdateDatetime()).append(",")
								.append(cusPos.getMacCode()).append(",")
								.append(cusPos.getLatitude()).append(",")
								.append(cusPos.getLongitude()).append(",")
								.append(cusPos.getAzimuth()).append(",")
								.append("01").append("' )");
						pst =conn.prepareStatement(sqlBuilder.toString());  
			            pst.executeUpdate();  
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	/** 
	 * 
	 */
	private static void startWriter(final Socket socket) {
		

		new Thread(){
			double latitude = -0.008069;
			double longitude = 112.520855;
			@Override
			public void run() {
				PrintWriter pout = null;
                try {
                	Thread.sleep(1000*2);
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),true);
                    String msg = new StringBuilder(String.valueOf(latitude)).append(",").append(longitude).toString();
                    pout.println(msg);
                    System.out.println("返回客户端的数据：" + msg);
                }catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			}
		}.start();
	}

	   static final String name = "com.mysql.jdbc.Driver";  
	   static final String url = ConfigUtil.get("jdbc.url"); 
	    public static final String user = ConfigUtil.get("jdbc.username"); 
	    public static final String password = ConfigUtil.get("jdbc.password"); 
	  
	    public static Connection conn = null;  
	    public static PreparedStatement pst = null;  
	    
	    public void connection() {  
	        try {  
	           
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}