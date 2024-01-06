package com.hpn.service.cr.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hpn.model.cr.CustomerOnlinePO;
import com.hpn.service.cr.CustomerOnlineServiceI;

import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.HqlFilter;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CustomerOnlineServiceImpl extends BaseServiceImpl<CustomerOnlinePO> implements CustomerOnlineServiceI {

	@Override
	public CustomerOnlinePO findLastLoginCustomer(String customerId,String macCode) {
		List<CustomerOnlinePO> pos = findLoginCustomers(customerId, macCode);
		if(pos.size()>0){
			return pos.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<CustomerOnlinePO> findLoginCustomers(String customerId, String macCode) {
		StringBuilder hqlBuilder = new StringBuilder("select c from CustomerOnlinePO c")
				.append(" WHERE c.customer.id ='").append(customerId).append("'");
		if(!StringUtils.isBlank(macCode)){
			hqlBuilder.append(" AND c.macCode ='").append(macCode).append("'");
		}		
		hqlBuilder.append(" ORDER BY c.loginDatetime desc");
		return find(hqlBuilder.toString());
	}

	@Override
	public Long countCustomerHistoryByFilter(HqlFilter hqlFilter) {
		String hql = "";
		return count(hql);
	}

	@Override
	public List<CustomerOnlinePO> findCustomerHistoryByFilter(HqlFilter hqlFilter, int page, int rows) {
		String hql = "";
		return find(hql);
	}

}
