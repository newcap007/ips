package com.hpn.action.nv;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletInputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.hpn.model.nv.IndoorMapPO;
import com.hpn.service.nv.CollectionsServiceI;
import com.hpn.service.nv.IndoorMapServiceI;

import zone.framework.action.BaseAction;
import zone.framework.model.easyui.Json;
import zone.framework.util.base.HqlFilter;

/**
 * 客户管理
 * 
 * action访问地址是/hpn/nv/spotData.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/hpn/nv")
@Action(value = "/indoorMap")
public class IndoorMapAction extends BaseAction<IndoorMapPO> {

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
	public void setService(IndoorMapServiceI service) {
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
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#number_S_EQ", data.getNumber());
			IndoorMapPO po = service.getByFilter(hqlFilter);
			if (po != null) {
				json.setMsg("新建地图失败，地图编码已存在！");
			} else {
				service.save(data);
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}
	
	/**
	 * 保存导览请求
	 */
	synchronized public void parseExcel() {
		Json json = new Json();
		try {
			if (data == null) {
				ServletInputStream inputStream = getRequest().getInputStream();
				OutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				while ((inputStream.read(buf)) != -1) {
					bos.write(buf, 0, buf.length);
				}
			}
		} catch (Exception e) { 
            e.printStackTrace();
        }	
		if (data != null) {
			
		}
		writeJson(json);
	}
}
