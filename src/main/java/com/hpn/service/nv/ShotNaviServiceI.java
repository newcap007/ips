package com.hpn.service.nv;

import java.util.Set;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.model.nv.ShotNaviPO;

import zone.framework.service.BaseServiceI;

public interface ShotNaviServiceI  extends BaseServiceI<ShotNaviPO> {

	/**
	 * 保存定点导览数据
	 * 
	 * @param spotData
	 * @param collectionss
	 * @return
	 */
	public Set<CollectionsPO> saveShotNavi(ShotNaviPO data, String imgUrl);


}
