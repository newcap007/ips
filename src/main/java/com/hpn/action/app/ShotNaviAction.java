package com.hpn.action.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.ShotNaviPO;
import com.hpn.service.nv.ShotNaviServiceI;

import sun.misc.BASE64Decoder;
import zone.framework.action.BaseAction;
import zone.framework.util.base.ConfigUtil;
import zone.framework.util.base.ImageBase64Util;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/nv/shotNavi.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/app/hpn")
@Action(value = "/shotNavi")
public class ShotNaviAction extends BaseAction<ShotNaviPO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式

	static String webSrcPath = ConfigUtil.get("uploadImage");

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(ShotNaviServiceI service) {
		this.service = service;
	}

	/**
	 * 获取导览当前位置的藏品信息
	 */
	synchronized public void findCollections() {
		try {
			if (data == null) {
				HttpServletRequest request = getRequest();
				request.setCharacterEncoding("utf-8");  
		        String macCode = request.getParameter("macCode");  
		        String operater = request.getParameter("operater");  
		        String photo = request.getParameter("photo");
		        data = new ShotNaviPO();
		        data.setPhoto(photo);
		        data.setMacCode(macCode);
		        data.setOperater(operater);
			}
	        com.hpn.action.nv.ShotNaviAction action = new com.hpn.action.nv.ShotNaviAction();
	        action.findCollections();
		} catch (Exception e) { 
            e.printStackTrace();
        }		
	}
}
