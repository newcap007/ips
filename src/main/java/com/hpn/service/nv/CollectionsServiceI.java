package com.hpn.service.nv;

import java.util.List;

import zone.framework.service.BaseServiceI;

import com.hpn.model.nv.CollectionsPO;

public interface CollectionsServiceI  extends BaseServiceI<CollectionsPO> {
	public List<CollectionsPO> findCollectionses(double latitude,double longitude, int azimuth);
}
