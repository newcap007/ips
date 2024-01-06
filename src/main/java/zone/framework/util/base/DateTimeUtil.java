package zone.framework.util.base;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	//重新设置图片名称
	public static String getStrToDatetime(String strname,String excelname){
		// 构建日期格式 
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); 
		String newname = strname+"["+df.format(new Date())+"]"+ excelname; 
		return newname;
	}
	
	//重新设置图片名称
	public static String getStrToDatetime(String excelname){
		// 构建日期格式 
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
		String newname = df.format(new Date())+excelname; 
		return newname;
	}
	
	/**
	* 日期转换成字符串
	* @param date
	* @return str
	*/
	public static String dateToStrMonth(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
	   String str = format.format(date);
	   return str;
	} 
	
	/**
	* 日期转换成字符串
	*/
	public static String dateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	   String str = format.format(date);
	   return str;
	} 
	
	/**
	 * @param path 文件夹路径  
	 */ 
	public static String isExist(String path, String name) {
		File file = new File(path+"/"+name);  
		//判断文件夹是否存在,如果不存在则创建文件夹  
		if (!file.exists()) {  
			file.mkdir();  
		}
		return file.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(dateToStrMonth(new Date()));
	}
}
