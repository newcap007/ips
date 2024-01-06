package com.hpn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThirdServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 接收数据
		String name = request.getParameter("name");
		String number = request.getParameter("number");
		String content = request.getParameter("content");
		/*
		 * if(name==null||name.length()==0||number==null||number.length()==0||
		 * content==null||content.length()==0){
		 * out.println("<h2 align=center>输入资料不完整！</h2>");
		 * out.println("<br><a href=\""+request.getContextPath()
		 * +"/add.html\"><h2 align=center>请重新输入</h2></A>"); }else{
		 * out.println("<h2 align=center>用户输入信息显示</h2><br>");
		 * out.println("<h2 align=center>物名："+name+"</h2>");
		 * out.println("<h2 align=center>RFID码："+number+"</h2>");
		 * out.println("<h2 align=center>备注 ："+content+"</h2>");
		 * out.println("<br><a href=\""+request.getContextPath()
		 * +"/welcome.html\"><h2 align=center>返回</h2></A>"); }
		 */
		// 保存到数据库
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/mine";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
			pst = conn.prepareStatement(
					"insert into first(name,number,content)values(" + name + "," + number + "," + content + ")");
			pst.setString(1, name);
			pst.setString(2, number);
			pst.setString(3, content);
			pst.executeUpdate();
			// 显示增加成功
			response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>增加小组成功</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1 align=center>增加小组成功</h1>");
			out.println("<p><a href='../add.html'>继续增加</a>");
			out.println("<a href='viewteams'>显示小组</a>");
			out.println("<a href='../welcome.html'>返回首页</a>");
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			e.printStackTrace();
			// 通过response向客户端应答，显示增加失败
			response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>增加小组失败</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1 align=center>增加小组失败</h1>");
			out.println("<p><a href='../add.html'>继续增加</a>");
			out.println("<a href='viewteams'>显示小组</a>");
			out.println("<a href='../welcome.html'>返回首页</a>");
			out.println("</body>");
			out.println("</html>");
		} finally {
			try {
				pst.close();
				conn.close();
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}