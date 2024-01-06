package com.hpn.action.nv;

import java.util.HashSet;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.SpotDataPO;
import com.hpn.service.nv.CollectionsServiceI;
import com.hpn.service.nv.SpotDataServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Json;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/nv/spotData.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/hpn/nv")
@Action(value = "/spotData")
public class SpotDataAction extends BaseAction<SpotDataPO> {

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
	public void setService(SpotDataServiceI service) {
		this.service = service;
	}
	
	@Autowired
	CollectionsServiceI collectionsService;


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
	synchronized public void findCollectionses() {
		if (data != null) {
			List<CollectionsPO> pos = collectionsService.findCollectionses(data.getLatitude(),data.getLongitude(),data.getAzimuth());
			((SpotDataServiceI)service).saveSpotData(data, pos);
			data.setCollectionses(new HashSet<CollectionsPO>(pos));
			writeJson(data);
		}
		
	}
}
