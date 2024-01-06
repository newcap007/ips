package com.hpn.service.cr.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpn.model.cr.CustomerPO;
import com.hpn.model.cr.CustomerReservePO;
import com.hpn.service.cr.CustomerOnlineServiceI;
import com.hpn.service.cr.CustomerReserveServiceI;
import com.hpn.service.cr.CustomerServiceI;

import zone.framework.service.impl.BaseServiceImpl;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CustomerReserveServiceImpl extends BaseServiceImpl<CustomerReservePO> implements CustomerReserveServiceI {

	@Autowired
	protected CustomerServiceI customerServiceI;// 业务逻辑
	
	@Override
	public Serializable save(CustomerReservePO o) {
		if(o.getCustomer()!=null){
			CustomerPO customerPO = customerServiceI.getById(o.getCustomer().getId());
			o.setCustomer(customerPO);
		}
		return baseDao.save(o);
		 
	}

}
