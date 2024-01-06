package com.hpn.action.cr;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.cr.CusTerminalLocPO;
import com.hpn.model.cr.CustomerPositionPO;
import com.hpn.model.mpoint.ConvertGauss2Geodetic;
import com.hpn.model.mpoint.MPoint;
import com.hpn.service.cr.CusTerminalLocServiceI;
import com.hpn.service.cr.CustomerPositionServiceI;

import zone.framework.action.BaseAction;

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
public class CusTerminalLocAction extends BaseAction<CusTerminalLocPO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	CustomerPositionServiceI cusPositionService;
	@Autowired
	CusTerminalLocServiceI service;
	
	@Autowired
	public void setService(CustomerPositionServiceI service) {
		this.cusPositionService = service;
	}
	@Autowired
	public void setService(CusTerminalLocServiceI service) {
		this.service = service;
	}
	
	public String convertGauss2Geodetic() {
		ConvertGauss2Geodetic convert = new ConvertGauss2Geodetic();
		List<CusTerminalLocPO> cusTerminalLocs = service.findNotConvertCusTerminalLocs();
		for(CusTerminalLocPO cusTerminalLoc:cusTerminalLocs){
			MPoint mpoint = convert.getBL(cusTerminalLoc.getX(), cusTerminalLoc.getY());
			CustomerPositionPO data = new CustomerPositionPO();
			data.setChannelType("02");
			data.setCreateDatetime(cusTerminalLoc.getRecvTime());
			data.setMacCode(cusTerminalLoc.getMuMac());
			data.setCusTerminalLoc(cusTerminalLoc);
			data.setLongitude(mpoint.x);
			data.setLatitude(mpoint.y);
			data.setUpdateDatetime(new Date());
			data.setOperater("system");
			this.cusPositionService.save(data);
		}
		return "";
	}
  
}
