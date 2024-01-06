package com.hpn.service.nv.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.ShotNaviPO;
import com.hpn.service.nv.CollectionsServiceI;
import com.hpn.service.nv.ShotNaviServiceI;
import com.hpn.util.base.HttpClientUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.HqlFilter;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class ShotNaviServiceImpl extends BaseServiceImpl<ShotNaviPO> implements ShotNaviServiceI {
	
	@Autowired
	CollectionsServiceI collectionsService;

	/**
	 * 保存定点导览数据
	 * @return
	 */
	@Override
	public Set<CollectionsPO> saveShotNavi(ShotNaviPO data, String imgUrl){
		save(data);
		Map<String, Object> params = new HashedMap<>();
		//imgUrl="https://img3.doubanio.com/view/status/raw/public/d9b811f3c51d843.jpg";
		params.put("imgurl", imgUrl);
		String picturnJson = HttpClientUtil.httpClientGet("http://47.94.129.191:5000/classify_url_sim", params, "UTF-8");
		JSONObject json = JSONObject.fromObject(picturnJson);
		String errorMessage = (String)json.get("ErrorMessage");
		logger.info(errorMessage);
		JSONArray pictureIds = (JSONArray)json.get("PredictionIDTop5");
		Set<CollectionsPO> collectionses = new HashSet<>();
		for(@SuppressWarnings("rawtypes")
		Iterator pictureId = pictureIds.iterator(); pictureId.hasNext();){
			String picNumber = String.valueOf(pictureId.next());
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#number_S_EQ", picNumber);
			CollectionsPO po = collectionsService.getByFilter(hqlFilter);
			if(po!=null){
				collectionses.add(po);
				//po.getShotNavis().add(data);.
				data.getCollectionses().add(po);
			}
		}
		return collectionses;
	};
}
