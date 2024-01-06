package com.hpn.service.cr;

import java.util.Date;
import java.util.List;

import com.hpn.model.cr.CusTerminalLocPO;
import com.hpn.model.cr.CustomerPositionPO;

import zone.framework.service.BaseServiceI;
/**
 * 客户业务
 * 
 * @author 刘领献
 * 
 */
public interface CusTerminalLocServiceI extends BaseServiceI<CusTerminalLocPO> {
	
	public List<CusTerminalLocPO> findNotConvertCusTerminalLocs ();	
}
