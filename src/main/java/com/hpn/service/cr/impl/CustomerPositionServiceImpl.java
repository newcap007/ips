package com.hpn.service.cr.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hpn.model.cr.CustomerPositionPO;
import com.hpn.service.cr.CustomerPositionServiceI;

import zone.framework.service.impl.BaseServiceImpl;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CustomerPositionServiceImpl extends BaseServiceImpl<CustomerPositionPO> implements CustomerPositionServiceI {
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	@Override
	public List<CustomerPositionPO> findCurrentCustomerPositions(String channelType, Date minTime,Date maxTime) {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat); 
		String max=sdf.format(maxTime);
		String min=sdf.format(minTime);   
		StringBuilder hqlBuilder = new StringBuilder("select c from CustomerPositionPO c")
				.append(" WHERE c.createDatetime between'").append(min).append("' and '").append(max).append("'");
		if(!StringUtils.isBlank(channelType)){
			hqlBuilder.append(" AND c.channelType ='").append(channelType).append("'");
		}		
		hqlBuilder.append(" GROUP BY c.macCode ");		
		hqlBuilder.append(" ORDER BY c.createDatetime desc");
		return find(hqlBuilder.toString());
	}

}
