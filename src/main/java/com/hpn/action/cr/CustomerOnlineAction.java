package com.hpn.action.cr;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.cr.CustomerOnlinePO;
import com.hpn.service.cr.CustomerOnlineServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Grid;
import zone.framework.util.base.HqlFilter;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/cr/customer.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/hpn/cr")
@Action(value = "/customerOnline")
public class CustomerOnlineAction extends BaseAction<CustomerOnlinePO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CustomerOnlineServiceI service;// 业务逻辑
	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(CustomerOnlineServiceI service) {
		this.service = service;
	}

	public void findCustomerHistory() {
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		grid.setTotal(service.countCustomerHistoryByFilter(hqlFilter));
		grid.setRows(service.findCustomerHistoryByFilter(hqlFilter, page, rows));
		writeJson(grid);
	}
}
