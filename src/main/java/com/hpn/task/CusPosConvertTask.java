package com.hpn.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hpn.action.cr.CusTerminalLocAction;
import com.hpn.model.cr.CusTerminalLocPO;
import com.hpn.model.cr.CustomerPositionPO;
import com.hpn.model.mpoint.ConvertGauss2Geodetic;
import com.hpn.model.mpoint.MPoint;
import com.hpn.service.cr.CusTerminalLocServiceI;
import com.hpn.service.cr.CustomerPositionServiceI;

import zone.framework.action.BaseAction;

@Component
public class CusPosConvertTask {
	
	private static final Logger logger = Logger.getLogger(CusPosConvertTask.class);
	
	@Resource
	CusTerminalLocServiceI service;
	@Resource
	CustomerPositionServiceI cusPositionService;

	@Scheduled(cron="0/10 * *  * * ? ")
	public void convertGauss2Geodetic(){
		if(true){
			return;
		}
		logger.info("转换高斯坐标的定时任务正在执行"+(new Date()));
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
	}
}
