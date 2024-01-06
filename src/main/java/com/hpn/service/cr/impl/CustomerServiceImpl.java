package com.hpn.service.cr.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpn.model.cr.CustomerOnlinePO;
import com.hpn.model.cr.CustomerPO;
import com.hpn.service.cr.CustomerOnlineServiceI;
import com.hpn.service.cr.CustomerServiceI;

import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.BeanUtils;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CustomerServiceImpl extends BaseServiceImpl<CustomerPO> implements CustomerServiceI {


	@Autowired
	CustomerOnlineServiceI customerOnlineService;

	public boolean updateCustomer(CustomerPO data){

//		HqlFilter hqlFilter = new HqlFilter();
//		hqlFilter.addFilter("QUERY_t#id_S_EQ", data.getId());
//		hqlFilter.addFilter("QUERY_t#deleteFlag_S_EQ", "0");
//		CustomerPO customer = getByFilter(hqlFilter);
		// 先检查用户存不存在
		List<CustomerPO> customer = findCustomersByNumber(data.getNumber());
		if (customer == null || customer.size() == 0) {
			logger.error("更新用户失败，该用户不存在！");
			return false;
		} else {
			String password = data.getPassword();
//			CustomerPO t = getById(data.getId());
			CustomerPO t = customer.get(0);
//			if(StringUtils.isBlank(password)){
//				password = t.getPassword();
//			}else{
//				password = MD5Util.md5(password);
//			}
			BeanUtils.copyNotNullProperties(data, t, new String[] {"id","password", "createDatetime" });
			t.setPassword(password);
			update(t);
			return true;
		}
	};
	/**
	 * 客户登录
	 */
	public boolean login(CustomerPO data) {
        // 先检查用户存不存在
		List<CustomerPO> customer = findCustomersByNumber(data.getNumber());
		if (customer == null || customer.size() == 0) {
			logger.error("客户登录失败，该用户不存在！");
			return false;
		}

		List<CustomerPO> customers = findCustomersByNumber(data.getNumber(),data.getPassword());
		if (customers.size() == 0) {
			logger.error("客户登录失败，客户的账号密码不正确");
			return false;
		} else if (customers.size() > 1) {
			logger.error("客户登录失败，客户数据异常，请联系管理员");
			return false;
		} else {
			List<CustomerOnlinePO> pos = customerOnlineService.findLoginCustomers(customers.get(0).getId(), null);
			for(CustomerOnlinePO po :pos ){
				po.setCustomer(customer.get(0));
				po.setStatus("1");
				po.setLogoutDatetime(new Date());
				customerOnlineService.update(po);
			}
			CustomerOnlinePO po = new CustomerOnlinePO();
			po.setMacCode(customer.get(0).getMacCode());
			po.setCustomer(customer.get(0));
			po.setStatus("0");
			po.setLogoutDatetime(null);
			customerOnlineService.save(po);
			return true;
		}
	
	}
	
	/**
	 * 客户退出
	 */
	public boolean logout(String customerId) {
//		HqlFilter hqlFilter = new HqlFilter();
//		hqlFilter.addFilter("QUERY_t#id_S_EQ", customerId);
//		hqlFilter.addFilter("QUERY_t#deleteFlag_S_EQ", "0");
		List<CustomerPO> customer = findCustomersByNumber(customerId);
		if (customer == null || customer.size() == 0) {
			logger.error("客户注销失败，该用户不存在，请联系管理员");
			return false;
		} else {
			CustomerOnlinePO po = customerOnlineService.findLastLoginCustomer(customer.get(0).getId(), null);
			po.setCustomer(customer.get(0));
			po.setStatus("1");
			po.setLogoutDatetime(new Date());
			customerOnlineService.update(po);
			return true;
		}
	}
	

	public List<CustomerPO> findCustomersByUnique (String number,String phoneNumber, String email){
		String hql = new StringBuilder("select number from CustomerPO c")
				.append(" WHERE c.deleteFlag ='0' ")
				.append(" AND (c.number ='").append(number).append("'")
				.append(" OR c.phoneNumber =' ").append(phoneNumber).append("'")
				.append(" OR c.email ='").append(email).append("')")
				.toString();
		List<CustomerPO> pos = find(hql);
		return pos;
	};
	
	public List<CustomerPO> findCustomersByNumber (String number){
		
		String hql = new StringBuilder("select c from CustomerPO c")
				.append(" WHERE (c.number ='").append(number).append("'")
				.append(" OR c.phoneNumber = '").append(number).append("'")
				.append(" OR c.email = '").append(number).append("')")
				.append(" AND c.deleteFlag ='0' ")
				.toString();
		List<CustomerPO> pos = find(hql);
		return pos;
	};
	
	public List<CustomerPO> findCustomersByNumber (String number,String password){
		
		String hql = new StringBuilder("select c from CustomerPO c")
				.append(" WHERE (c.number ='").append(number).append("'")
				.append(" OR c.phoneNumber = '").append(number).append("'")
				.append(" OR c.email = '").append(number).append("')")
				.append(" AND c.password ='").append(password).append("'")
				.append(" AND c.deleteFlag ='0' ")
				.toString();
		List<CustomerPO> pos = find(hql);
		return pos;
	};
}
