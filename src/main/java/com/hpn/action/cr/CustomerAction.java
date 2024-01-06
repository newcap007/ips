package com.hpn.action.cr;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.cr.CustomerOnlinePO;
import com.hpn.model.cr.CustomerPO;
import com.hpn.service.cr.CustomerOnlineServiceI;
import com.hpn.service.cr.CustomerServiceI;

import zone.framework.action.BaseAction;
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
@Namespace("/hpn/cr")
@Action
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

	@Autowired
	CustomerOnlineServiceI customerOnlineService;
	
	/**
	 * 新建一个客户
	 */
	synchronized public void save() {
		Json json = new Json();
		if (data != null) {
			List<CustomerPO> customers = ((CustomerServiceI)service).findCustomersByUnique(data.getNumber(),data.getPhoneNumber(),data.getEmail());
			if (customers.size() > 0) {
				json.setMsg("新建用户失败，用户名该用户已存在！（用户名、电话号码或邮件地址重复）");
			} else {
				data.setPassword(MD5Util.md5(data.getPassword()));
				service.save(data);
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}

	/**
	 * 更新一个用户
	 */
	synchronized public void update() {
		Json json = new Json();
		json.setMsg("更新用户失败，该用户不存在！");
		if (data != null && !StringUtils.isBlank(data.getId())
				&&((CustomerServiceI)service).updateCustomer(data)) {
			json.setSuccess(true);
			json.setMsg("更新成功！");
		}
		writeJson(json);
	}
	
	/**
	 * 客户登录
	 */
	synchronized public void login() {
		Json json = new Json();
		json.setMsg("登录失败！请核对账号密码是否正确");
		if (data != null && !StringUtils.isBlank(data.getId())) {
			
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#id_S_EQ", data.getId());
			hqlFilter.addFilter("QUERY_t#deleteFlag_S_EQ", "0");
			CustomerPO customer = service.getByFilter(hqlFilter);
			if (customer == null) {
				//logger.error("客户登录失败，该用户不存在！");
			}

			List<CustomerPO> customers = ((CustomerServiceI)service).findCustomersByNumber(data.getNumber(),data.getPassword());
			if (customers.size() == 0) {
			} else if (customers.size() > 1) {
			} else {
				List<CustomerOnlinePO> pos = customerOnlineService.findLoginCustomers(data.getId(), null);
				for(CustomerOnlinePO po :pos ){
					po.setCustomer(customer);
					po.setStatus("1");
					po.setLogoutDatetime(new Date());
					customerOnlineService.update(po);
				}
				CustomerOnlinePO po = new CustomerOnlinePO();
				po.setMacCode(customer.getMacCode());
				po.setCustomer(customer);
				po.setStatus("0");
				po.setLogoutDatetime(null);
				customerOnlineService.save(po);
				
			json.setSuccess(true);
			json.setMsg("登录成功！");	
			}
		}
		writeJson(json);
	}
	
	/**
	 * 客户注销
	 */
	synchronized public void logout() {
		Json json = new Json();
		json.setMsg("注销失败！");
		if (id != null && !StringUtils.isBlank(id)
				&&((CustomerServiceI)service).logout(id)) {
			json.setSuccess(true);
			json.setMsg("注销成功！");
		}
		writeJson(json);
	}
	
	/**
	 * 更新一个用户
	 */
	synchronized public void delete() {
		Json json = new Json();
		json.setMsg("更新失败！");
		if (id != null && !StringUtils.isBlank(id)) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#id_S_EQ", id);
			CustomerPO customer = service.getByFilter(hqlFilter);
			if (customer == null) {
				json.setMsg("更新用户失败，该用户不存在！");
			} else {
				CustomerPO t = service.getById(id);
				t.setDeleteFlag("1");
				service.update(t);
				json.setSuccess(true);
				json.setMsg("更新成功！");
			}
		}
		writeJson(json);
	}

}
