package com.hpn.service.nv.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.hpn.model.nv.CollectionsPO;
import com.hpn.service.nv.CollectionsUploadServiceI;

import zone.framework.service.impl.BaseServiceImpl;

/**
 * 客户管理
 * 
 * @author 刘领献
 * 
 */
@Service
public class CollectionsUploadServiceImpl extends BaseServiceImpl<CollectionsPO> implements CollectionsUploadServiceI {

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
}
