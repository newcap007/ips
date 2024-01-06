package com.hpn.action.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hpn.model.cr.CustomerPO;
import com.hpn.service.cr.CustomerServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Json;
import zone.framework.util.base.BeanUtils;
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
@Action(value = "/customer")
public class CustomerAction extends BaseAction<CustomerPO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(CustomerServiceI service) {
		this.service = service;
	}

	/**
	 * 新建一个客户
	 */
	synchronized public void save() {
		try {
			Json json = new Json();
			if (data == null) {
				data = getVoFromRequest();
			}
			List<CustomerPO> customers = ((CustomerServiceI) service).findCustomersByUnique(data.getNumber(),
					data.getPhoneNumber(), data.getEmail());
			if (customers.size() > 0) {
				json.setMsg("新建用户失败，用户名该用户已存在！（用户名、电话号码或邮件地址重复）");
			} else {
				service.save(data);
				json.setSuccess(true);
			}

			writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新一个用户
	 */
	synchronized public void update() {
		try {
			Json json = new Json();
			json.setMsg("更新失败！");
			if (data == null) {
				data = getVoFromRequest();
			}			
			if (data != null && !StringUtils.isBlank(data.getId())
					&&((CustomerServiceI)service).updateCustomer(data)) {
				json.setSuccess(true);
				json.setMsg("更新成功！");
			}
			writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户登录
	 */
	synchronized public void login() {
		try {
			Json json = new Json();
			json.setMsg("登录失败！请核对账号密码是否正确");
			JSONObject requestJson = new JSONObject();
			if (data == null) {
				data = getVoFromRequest();
			}
			if (data != null && ((CustomerServiceI)service).login(data)) {
//				json.setSuccess(true);
//				json.setMsg("登录成功！");
				requestJson.put("success", true);
				requestJson.put("userName", data.getNumber());
				requestJson.put("msg", "登录成功！");
			}
			writeJson(requestJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户注销
	 */
	synchronized public void logout() {
		try {
			if (data == null) {
				data = getVoFromRequest();
			}
			Json json = new Json();
			json.setMsg("注销失败！");
			if (data.getNumber() != null && !StringUtils.isBlank(data.getNumber())
					&&((CustomerServiceI)service).logout(data.getNumber())) {
				json.setSuccess(true);
				json.setMsg("注销成功！");
			}
			writeJson(json);				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	synchronized public void query() {
		try {
			Json json = new Json();
			if (data == null) {
				data = getVoFromRequest();
			}
			List<CustomerPO> customers = ((CustomerServiceI) service).findCustomersByNumber(data.getNumber());
			if (customers.size() == 0){
				json.setMsg("用户查询失败，该用户重复存在！");
			}else if (customers.size() > 1) {
				json.setMsg("用户查询失败，该用户不存在！");
			} else {
				json.setObj(customers.get(0));
				json.setSuccess(true);
			}

			writeJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private CustomerPO getVoFromRequest() throws IOException {
		
		ServletInputStream inputStream = getRequest().getInputStream();
		OutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		while ((inputStream.read(buf)) != -1) {
			bos.write(buf, 0, buf.length);
		}
		JSONObject requestJson = JSONObject.parseObject(bos.toString());
		CustomerPO vo = new CustomerPO();
		vo.setId(requestJson.getString("id"));
		vo.setBirthday(requestJson.getDate("birthday"));
		vo.setEmail(requestJson.getString("email"));
		vo.setIdCode(requestJson.getString("idCode"));
		if(!StringUtils.isEmpty(requestJson.getString("name"))){
			vo.setName(URLDecoder.decode(requestJson.getString("name"), "UTF-8"));
		} else {
			vo.setName(requestJson.getString("name"));
		}
		
		vo.setNumber(requestJson.getString("number"));
		vo.setOccupation(requestJson.getString("occupation"));
		vo.setOperater(requestJson.getString("operater"));
		vo.setPassword(MD5Util.md5(requestJson.getString("password")));
		vo.setPhoneNumber(requestJson.getString("phoneNumber"));
		vo.setPhoto(requestJson.getString("photo"));
		vo.setSecondPhoneNumber(requestJson.getString("secondPhoneNumber"));
		vo.setSex(requestJson.getString("sex"));
		vo.setDeleteFlag("0");
		bos.close();
		return vo;
	}

}
