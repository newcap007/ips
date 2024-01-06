package com.hpn.action.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hpn.model.cr.CustomerPO;
import com.hpn.model.cr.CustomerReservePO;
import com.hpn.model.nv.ShotNaviPO;
import com.hpn.service.cr.CustomerOnlineServiceI;
import com.hpn.service.cr.CustomerReserveServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Grid;
import zone.framework.model.easyui.Json;
import zone.framework.util.base.HqlFilter;
import zone.framework.util.base.MD5Util;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/cr/customer.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/app/hpn")
@Action(value = "/customerReserve")
public class CustomerReserveAction extends BaseAction<CustomerReservePO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/*
	protected CustomerReserveServiceI service;// 业务逻辑
	*/
	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(CustomerReserveServiceI service) {
		this.service = service;
	}

	/**
	 * 保存一个对象
	 */
	public void save() {
		Json json = new Json();
		try {
			if (data == null) {
				HttpServletRequest request = getRequest();
				request.setCharacterEncoding("utf-8");  
		        data = new CustomerReservePO();
		        data.setRemarks(request.getParameter("remarks"));
		        data.setReserveNum(Integer.valueOf(request.getParameter("reserveNum")));
		        data.setReserveDate(format.parse(request.getParameter("reserveDate")));
		        data.setOperater(request.getParameter("operater"));
		        data.getCustomer().setId(request.getParameter("customer.id"));
			}
			service.save(data);
			json.setSuccess(true);
			json.setMsg("新建成功！");
		} catch (Exception e) { 
            e.printStackTrace();
        }		
		writeJson(json);
		
	}
	
}
