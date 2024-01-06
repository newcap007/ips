package com.hpn.service.nv;

import java.util.List;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.SpotDataPO;

import zone.framework.service.BaseServiceI;

public interface SpotDataServiceI  extends BaseServiceI<SpotDataPO> {

	/**
	 * 保存定点导览数据
	 * 
	 * @param spotData
	 * @param collectionss
	 * @return
	 */
	public void saveSpotData(SpotDataPO spotData, List<CollectionsPO> collectionss);


}
