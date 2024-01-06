package com.hpn.service.nv.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zone.framework.dao.base.BaseDaoI;
import zone.framework.service.impl.BaseServiceImpl;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.service.nv.CollectionsServiceI;
import com.hpn.service.nv.CollectionsUploadServiceI;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CollectionsServiceImpl extends BaseServiceImpl<CollectionsPO> implements CollectionsServiceI ,CollectionsUploadServiceI {

	@Autowired
	private BaseDaoI<CollectionsPO> baseDao;
	
	@Override
	public List<CollectionsPO> findCollectionses(double latitude,double longitude, int azimuth) {
		double raidus=0.001;
		String hql = new StringBuilder("select c from CollectionsPO c")
				.append(" WHERE c.latitude >").append(latitude).append(" - ").append(raidus)
				.append(" AND c.latitude < ").append(latitude).append(" + ").append(raidus)
				.append(" AND c.longitude >").append(longitude).append(" - ").append(raidus)
				.append(" AND c.longitude < ").append(longitude).append(" + ").append(raidus)
				.append(" ORDER BY ACOS( SIN((").append(latitude)
				.append(" * 3.1415) / 180) * SIN((c.latitude * 3.1415) / 180) + COS(( ").append(latitude)
				.append(" * 3.1415) / 180) * COS((c.latitude * 3.1415) / 180) * COS(( ").append(longitude)
				.append(" * 3.1415) / 180 - (c.longitude * 3.1415) / 180 )) * 6380 ASC ")
				.toString();
		List<CollectionsPO> pos = find(hql);
		return pos;
		}
	
	@Override
	public boolean upload(String operater, File file) {
		//String filePath = "E:/SVN/doc/08_系统运维/import/典当系统用表1.xls";
		try {
			InputStream is = new FileInputStream(file.getAbsolutePath());
			XSSFWorkbook wb = new XSSFWorkbook(is);  
			int scounts = wb.getNumberOfSheets();//获取表的总数  
			for(int i =0 ; i<scounts; i++){
					Sheet sheet = wb.getSheetAt(i);
					int j = 0;
			        for (Row row : sheet) {
			        	CollectionsPO collections = new CollectionsPO();
			        	if(j++==0||row.getCell(0)==null){
			        		continue;
			        	}
			        	collections.setNumber(String.valueOf(row.getCell(0).getNumericCellValue()));
			        	collections.setName(row.getCell(1).getStringCellValue());
			        	collections.setLatitude(row.getCell(2).getNumericCellValue());
			        	collections.setLongitude(row.getCell(3).getNumericCellValue());
			        	collections.setPictureUrl(row.getCell(1).getStringCellValue());
			        	collections.setVoiceUrl(row.getCell(5).getStringCellValue());
			        	collections.setCommentText(row.getCell(1).getStringCellValue());
			        	collections.setOperater(operater);
			        	save(collections);
			        }
			}
			// 关闭
	        is.close();
	        wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	@Override
	public Serializable save(CollectionsPO collections) {
		return baseDao.save(collections);
		
	}
}
