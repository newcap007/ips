package com.hpn.service.nv;

import java.io.File;

import com.hpn.model.nv.CollectionsPO;

import zone.framework.service.BaseServiceI;

public interface CollectionsUploadServiceI  extends BaseServiceI<CollectionsPO> {
	
	public boolean upload(String operater, File file);
}
