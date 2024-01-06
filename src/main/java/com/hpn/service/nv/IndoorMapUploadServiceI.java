package com.hpn.service.nv;

import java.io.File;

import com.hpn.model.nv.IndoorMapPO;

import zone.framework.service.BaseServiceI;

public interface IndoorMapUploadServiceI  extends BaseServiceI<IndoorMapPO> {
	
	public boolean upload(String operater, File file);
}
