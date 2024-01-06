package com.hpn.action.app;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.SpotDataPO;
import com.hpn.service.nv.CollectionsServiceI;
import com.hpn.service.nv.SpotDataServiceI;

import zone.framework.action.BaseAction;
/**
 * 客户管理
 * 
 * action访问地址是/app/hpn/spotData.do
 * 
 * @author 刘领献
 * 
 */
@Namespace("/app/hpn")
@Action(value = "/spotData")
public class SpotDataAction extends BaseAction<SpotDataPO>{

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
	 * 获取导览当前位置的藏品信息
	 */
	public void findCollectionses() {
		try {
			if (data == null) {
				ServletInputStream inputStream = getRequest().getInputStream();
				OutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				while ((inputStream.read(buf)) != -1) {
					bos.write(buf, 0, buf.length);
				}
				JSONObject requestJson = JSONObject.parseObject(bos.toString());
				data = new SpotDataPO();
				data.setMacCode(requestJson.getString("macCode"));
				data.setLatitude(requestJson.getDoubleValue("latitude"));
				data.setLongitude(requestJson.getDoubleValue("longitude"));
				data.setAzimuth(requestJson.getIntValue("azimuth"));
				data.setOperater(requestJson.getString("operater"));
				bos.close();
			}
			
			List<CollectionsPO> pos = collectionsService.findCollectionses(data.getLatitude(),data.getLongitude(),data.getAzimuth());
			((SpotDataServiceI)service).saveSpotData(data, pos);
			data.setCollectionses( new HashSet<CollectionsPO>(pos));
			//service.save(data);
			writeJson(data);
		} catch (Exception e) { 
            e.printStackTrace();
        }
	}
	
}
