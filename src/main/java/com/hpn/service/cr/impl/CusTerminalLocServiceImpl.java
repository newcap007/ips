package com.hpn.service.cr.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hpn.model.cr.CusTerminalLocPO;
import com.hpn.service.cr.CusTerminalLocServiceI;

import zone.framework.service.impl.BaseServiceImpl;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CusTerminalLocServiceImpl extends BaseServiceImpl<CusTerminalLocPO> implements CusTerminalLocServiceI {
	private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

	@Override
	public List<CusTerminalLocPO> findNotConvertCusTerminalLocs () {
		StringBuilder hqlBuilder = new StringBuilder("select c from CusTerminalLocPO c")
				.append(" WHERE c.recvTime not in (select d.createDatetime from CustomerPositionPO d)");
		hqlBuilder.append(" ORDER BY c.recvTime asc");
		return find(hqlBuilder.toString());
	}

}
