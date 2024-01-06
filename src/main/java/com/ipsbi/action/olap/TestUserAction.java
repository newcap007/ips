package com.ipsbi.action.olap;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.ipsbi.model.olap.TestUserPO;
import com.ipsbi.service.olap.TestUserServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Json;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/cr/customer.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/ipsbi/olap")
@Action(value = "/testUser")
public class TestUserAction extends BaseAction<TestUserPO> {

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
	public void setService(TestUserServiceI service) {
		this.service = service;
	}

	/**
	 * 新建一个客户
	 */
	synchronized public void save() {
		Json json = new Json();
		if (data != null) {
			
				service.save(data);
				json.setSuccess(true);
		}
		writeJson(json);
	}
}
