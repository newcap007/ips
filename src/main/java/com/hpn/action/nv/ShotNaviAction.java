package com.hpn.action.nv;

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

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Json;
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
@Namespace("/hpn/nv")
@Action(value = "/shotNavi")
public class ShotNaviAction extends BaseAction<ShotNaviPO> {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式
	static String webSrcPath = ConfigUtil.get("uploadImage");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * 保存导览请求
	 */
	synchronized public void save() {
		Json json = new Json();
		if (data != null) {
			service.save(data);
			json.setSuccess(true);
		}
		writeJson(json);
	}
	/**
	 * 获取导览当前位置的藏品信息
	 */
	synchronized public void findCollections() {
		if (data != null) {
			//处理拍摄的客户的照片
			if(!StringUtils.isBlank(data.getPhoto())){
				String webPath = Thread.currentThread().getContextClassLoader().getResource("").getPath() ;
				webPath = new StringBuilder(webPath).append("../../").append(webSrcPath).toString();
				String fileName = new StringBuilder(dateFormat.format(new Date())).append(".jpg").toString();
				ImageBase64Util.makeOriginalImg(data.getPhoto(),webPath, fileName);
				//String contextPath =  getSession().getServletContext().getContextPath();
				data.setPhotoUrl(new StringBuilder(webSrcPath).append(fileName).toString());				
			}
			HttpServletRequest request = getRequest();
			String imgUrl = new StringBuilder("http://").append(request.getServerName())//服务器地址  
					.append( ":").append(request.getServerPort())           //端口号  
					.append(request.getContextPath())
					.append(data.getPhotoUrl()).toString();      //项目名称  
			Set<CollectionsPO> collectionses = ((ShotNaviServiceI)service).saveShotNavi(data,imgUrl);
			data.setCollectionses(collectionses);
			writeJson(data);
		}
		
	}
}
