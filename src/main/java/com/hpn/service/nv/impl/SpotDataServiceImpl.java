package com.hpn.service.nv.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.SpotDataPO;
import com.hpn.service.nv.SpotDataServiceI;

import zone.framework.service.impl.BaseServiceImpl;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class SpotDataServiceImpl extends BaseServiceImpl<SpotDataPO> implements SpotDataServiceI {

	/**
	 * 保存定点导览数据
	 * @return
	 */
	@Override
	public void saveSpotData(SpotDataPO spotData, List<CollectionsPO> collectionses){
		save(spotData);
		for(CollectionsPO collections :collectionses){
			collections.getSpotDatas().add(spotData);
		}
	};
}
