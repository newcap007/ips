package com.hpn.action.app;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Iterator;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.hpn.model.cr.CustomerPositionPO;

import zone.framework.util.base.ConfigUtil;

public class PositionLiveDataSocketNio{
	
	//Socket协议服务端  
    private int port = 60301;  
    private ServerSocketChannel serverSocketChannel;  
    private Charset charset = Charset.forName("UTF-8");  
    private Selector selector = null;
    
    static final String name = "com.mysql.jdbc.Driver";  
	static final String url = ConfigUtil.get("jdbc.url"); 
	public static final String user = ConfigUtil.get("jdbc.username"); 
	public static final String password = ConfigUtil.get("jdbc.password"); 
	  
	public static Connection conn = null;
	public static PreparedStatement pst = null;
    
    public PositionLiveDataSocketNio() throws IOException {  
        selector = Selector.open();  
        serverSocketChannel = ServerSocketChannel.open();  
        serverSocketChannel.socket().setReuseAddress(true);  
        serverSocketChannel.socket().bind(new InetSocketAddress(port));  
        System.out.println("服务器启动");  
    }
    
    /* 编码过程 */  
    public ByteBuffer encode(String str) {  
        return charset.encode(str);  
    }  
  
    /* 解码过程 */  
    public String decode(ByteBuffer bb) {  
        return charset.decode(bb).toString();  
    }

	public static void main(String[] args) throws IOException {
		new PositionLiveDataSocketNio().startService();
	}

	private void startService() {
		try {
			serverSocketChannel.configureBlocking(false);  
	        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  
	        /** 外循环，已经发生了SelectionKey数目 */  
	        while (selector.select() > 0) {
	            /* 得到已经被捕获了的SelectionKey的集合 */  
	            Iterator iterator = selector.selectedKeys().iterator();  
	            while (iterator.hasNext()) {
	                SelectionKey key = null;  
	                try {
	                    key = (SelectionKey) iterator.next();
	                    iterator.remove();
	                    if (key.isAcceptable()) {
	                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
	                        SocketChannel sc = ssc.accept();
	                        System.out.println("客户端机子的地址是 "  
	                                        + sc.socket().getRemoteSocketAddress()  
	                                        + "  客户端机机子的端口号是 "  
	                                        + sc.socket().getLocalPort());
	                        sc.configureBlocking(false);
	                        ByteBuffer buffer = ByteBuffer.allocate(1024);
	                        sc.register(selector, SelectionKey.OP_READ , buffer);//buffer通过附件方式，传递  
	                    }
	                    if (key.isReadable()) {
	                        reveice(key);
	                    }
	                    if (key.isWritable()) {
//	                       send(key);
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    try {
	                        if (key != null) {
	                            key.cancel();
	                            key.channel().close();
	                        }
	                    } catch (ClosedChannelException cex) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	            /* 内循环完 */  
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
	// 定义实现编码、解码的字符串集对象  
    private Charset charse = Charset.forName("utf-8");  
	  
    /* 接收 */  
    public void reveice(SelectionKey key) throws IOException, SQLException {  
        if (key == null)  
            return;  
        //***用SelectionKey.attachment()获取客户端消息***//  
        //：通过附件方式，接收数据  
//       ByteBuffer buff = (ByteBuffer) key.attachment();  
        // SocketChannel sc = (SocketChannel) key.channel();  
//       buff.limit(buff.capacity());  
        // buff.position(0);  
        // sc.read(buff);  
        // buff.flip();  
        // String reviceData = decode(buff);  
        // System.out.println("接收：" + reviceData);  
  
        //***用channel.read()获取客户端消息***//  
        //：接收时需要考虑字节长度        
        SocketChannel sc = (SocketChannel) key.channel();  
        String content = "";  
        //create buffer with capacity of 48 bytes         
        ByteBuffer buf = ByteBuffer.allocate(1024);//java里一个(utf-8)中文3字节,gbk中文占2个字节      
        int bytesRead = sc.read(buf); //read into buffer.  
        System.out.println("*读取客户端输入*");
        while (bytesRead >0) {
            buf.flip();  //make buffer ready for read  
            while(buf.hasRemaining()){
                buf.get(new byte[buf.limit()]); // read 1 byte at a time    
                content += new String(buf.array());  
            }                   
          buf.clear(); //make buffer ready for writing        
          bytesRead = sc.read(buf);
        }

        key.interestOps(SelectionKey.OP_READ); 
        System.out.println("客户端输入值为：" + content.trim());
        CustomerPositionPO cusPos = (CustomerPositionPO)JSON.parseObject(content, CustomerPositionPO.class);
        
		String sql = "insert into hpn_customerPosition (id, createDatetime, deleteFlag, operater, updateDatetime, azimuth, latitude, longitude, MACCode, channelType )"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		if(conn == null || conn.isClosed()){
			conn = DriverManager.getConnection(url, user, password);//获取连接  
		}
		
		pst =conn.prepareStatement(sql);
		java.util.Date utilDate= new java.util.Date();
		java.sql.Timestamp sqlDate=new java.sql.Timestamp(utilDate.getTime());
		
		pst.setString(1, UUID.randomUUID().toString());
		pst.setTimestamp(2, sqlDate);
		pst.setString(3, "0");
		pst.setString(4, cusPos.getOperater());
		pst.setTimestamp(5, sqlDate);
		pst.setInt(6, cusPos.getAzimuth());
		pst.setDouble(7, cusPos.getLatitude());
		pst.setDouble(8, cusPos.getLongitude());
		pst.setString(9, cusPos.getMacCode());
		pst.setString(10, "01");
		
        pst.executeUpdate();
 
    }  
      
    int y = 0;  
    public void send(SelectionKey key) {  
        System.out.println("send2() " +(++y));  
    }  
  
    /* 发送文件 */  
    public void sendFile(SelectionKey key) {  
        if (key == null)  
            return;  
        ByteBuffer buff = (ByteBuffer) key.attachment();  
        SocketChannel sc = (SocketChannel) key.channel();  
        String data = decode(buff);  
        if (data.indexOf("get") == -1)  
            return;  
        String subStr = data.substring(data.indexOf(" "), data.length());  
        System.out.println("截取之后的字符串是 " + subStr);  
        FileInputStream fileInput = null;  
        try {  
            fileInput = new FileInputStream(subStr);  
            FileChannel fileChannel = fileInput.getChannel();  
            fileChannel.transferTo(0, fileChannel.size(), sc);    
            fileChannel.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                fileInput.close();                
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
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

    public void connection() {  
        try {  
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}