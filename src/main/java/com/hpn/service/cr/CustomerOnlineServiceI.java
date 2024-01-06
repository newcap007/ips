package com.hpn.service.cr;

import java.util.List;

import com.hpn.model.cr.CustomerOnlinePO;

import zone.framework.service.BaseServiceI;
import zone.framework.util.base.HqlFilter;
/**
 * 客户业务
 * 
 * @author 刘领献
 * 
 */
public interface CustomerOnlineServiceI extends BaseServiceI<CustomerOnlinePO> {	
	public CustomerOnlinePO findLastLoginCustomer (String customerId,String macCode);	
	public List<CustomerOnlinePO> findLoginCustomers (String customerId,String macCode);
	/**
	 * 统计数目
	 * 
	 * @param hqlFilter
	 * @return
	 */
	public Long countCustomerHistoryByFilter(HqlFilter hqlFilter);
	/**
	 * 获得分页后的对象列表
	 * 
	 * @param hqlFilter
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<CustomerOnlinePO> findCustomerHistoryByFilter(HqlFilter hqlFilter, int page, int rows);
}
