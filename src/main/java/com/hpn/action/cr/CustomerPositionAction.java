package com.hpn.action.cr;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.cr.CustomerPositionPO;
import com.hpn.service.cr.CustomerPositionServiceI;

import zone.framework.action.BaseAction;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/cr/customer.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/hpn/cr")
@Action
public class CustomerPositionAction extends BaseAction<CustomerPositionPO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CustomerPositionServiceI service;
	
	@Autowired
	public void setService(CustomerPositionServiceI service) {
		this.service = service;
	}
	
	public String findCurrentCustomer() {
		Date maxTime=new Date();  
		Date minTime=new Date(maxTime.getTime()-5000);
		List<CustomerPositionPO> customerPositions = service.findCurrentCustomerPositions("01",minTime,maxTime);
		return dataCreater(customerPositions);
	}

	private String dataCreater(List<CustomerPositionPO> customerPositions) {
		StringBuilder resultBuilder = new StringBuilder("{").append("\"type\": \"FeatureCollection\",")
				.append("\"features\": [");
		StringBuilder recordBuilder = new StringBuilder();
		for (CustomerPositionPO customerPosition : customerPositions) {			
			recordBuilder.append("{ \"type\": \"Feature\", \"geometry\": { \"type\": \"Point\", \"coordinates\": [ ")
					.append(customerPosition.getLongitude()).append(", ").append(customerPosition.getLatitude()).append(" ] } },");
		}
		recordBuilder.setLength(recordBuilder.length() - 1);
		resultBuilder.append(recordBuilder).append("]").append("}");
		return resultBuilder.toString();
	}  
}
