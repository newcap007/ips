package com.hpn.websocket;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hpn.model.cr.CustomerPositionPO;
import com.hpn.service.cr.CustomerPositionServiceI;

import zone.framework.action.BaseAction;
import zone.framework.util.base.StringUtil;

public class CustomerPositionThread extends Thread {
	private static final Logger logger = Logger.getLogger(CustomerPositionThread.class);
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";
	private Session session;
	Date startTime=new Date();
	int timeWidth=2000;
	int periodic=5000;
	boolean review = false;

	CustomerPositionServiceI service;
	public CustomerPositionThread(Session session,String message) {
		if(message!=null){
			String data[] = message.split(" and ");
			SimpleDateFormat sdf=new SimpleDateFormat(dateFormat); 
			if(data.length==0 || StringUtils.isBlank(data[0])){
				review = false;
			} else {
				review = true;
				try {
					startTime = sdf.parse(data[0]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (data.length>1 && !StringUtils.isBlank(data[1])) {
				timeWidth = Integer.parseInt(data[1]) * 1000;
			}
			if (data.length>2 && !StringUtils.isBlank(data[2])) {
				periodic = Integer.parseInt(data[2]) * 1000;
			}
		}
		
		if(service==null){
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
			service = (CustomerPositionServiceI)wac.getBean("customerPositionServiceImpl");
		}
		this.session = session;

	}
	
	@Override
	public void run() {

		do{
			try {
				// 1分=60000毫秒，这是10秒触发一次
				Thread.sleep(periodic);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Date minTime = startTime;
			Date maxTime = new Date(minTime.getTime() + timeWidth);
			List<CustomerPositionPO> customerPositions = service.findCurrentCustomerPositions(null, minTime, maxTime);
			// 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
			try {
				session.getBasicRemote().sendText(dataCreater(customerPositions));
			} catch (IOException e) {
				try {
					session.getBasicRemote().sendText(e.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}while(!review);

	}
	
	private String dataCreater(List<CustomerPositionPO> customerPositions) {
		StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"FeatureCollection\",")
				.append("\"features\": [");
		StringBuilder recordBuilder = new StringBuilder();
		for (CustomerPositionPO customerPosition : customerPositions) {			
			recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
					.append(customerPosition.getLongitude()).append(", ").append(customerPosition.getLatitude()).append(" ] } },");
		}
		if(recordBuilder.length()!=0){
			recordBuilder.setLength(recordBuilder.length() - 1);
		}
		resultBuilder.append(recordBuilder).append("]").append("}");
		logger.info("##jeojson##"+resultBuilder.toString());
		return resultBuilder.toString();
	}  
}
