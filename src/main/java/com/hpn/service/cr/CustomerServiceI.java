package com.hpn.service.cr;

import java.util.List;

import com.hpn.model.cr.CustomerPO;

import zone.framework.service.BaseServiceI;
/**
 * 客户业务
 * 
 * @author 刘领献
 * 
 */
public interface CustomerServiceI extends BaseServiceI<CustomerPO> {	
	public List<CustomerPO> findCustomersByUnique (String number,String phoneNumber, String email);	
	public List<CustomerPO> findCustomersByNumber (String number,String password);
	public List<CustomerPO> findCustomersByNumber (String number);	
	public boolean login(CustomerPO data);
	public boolean logout(String customerId);
	public boolean updateCustomer(CustomerPO data);
}
