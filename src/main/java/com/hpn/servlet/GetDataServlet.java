package com.hpn.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @说明 该Servlet将本地硬盘的图片输入管道中
 * @author cuisuqiang
 * @version 1.0
 * @since
 */
@SuppressWarnings("serial")
public class GetDataServlet extends HttpServlet  {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    String parameter = request.getParameter("username");
	    String parameter2 = request.getParameter("password");
	    System.out.println(parameter + parameter2);
	    response.setContentType("text/*;charset=utf-8");
	    response.getWriter().write(parameter + "-" + parameter2);
	}
	 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    // doGet(request, response);
	    System.out.println("post");
	    ServletInputStream inputStream = request.getInputStream();
		OutputStream b = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    while ((inputStream.read(buf)) != -1) {
	        b.write(buf, 0, buf.length);
	    }
	    String s = b.toString();
	    String[] split = s.split("&");
//	    JSONObject rs = JSONObject.fromObject(s);
	    
	    System.out.println(split[0] + split[1]);
//	    System.out.println(rs);
	    response.setContentType("text/*;charset=utf-8");
	    response.getWriter().write(split.toString());
	}
}
