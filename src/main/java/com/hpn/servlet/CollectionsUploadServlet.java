package com.hpn.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.hpn.model.nv.CollectionsPO;
import com.hpn.service.nv.CollectionsUploadServiceI;

import zone.framework.model.base.SessionInfo;
import zone.framework.util.base.ConfigUtil;
import zone.framework.util.base.DateUtil;

/**
 * plUpload上传工具
 * 
 * @author linux liu
 * 
 */
public class CollectionsUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CollectionsUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileFolder = request.getParameter("fileFolder");// 前台传递过来的文件夹参数，如果有就在这个目录里面保存文件
		if (StringUtils.isBlank(fileFolder)) {
			fileFolder = "/temp";// 避免前台没有传递这个参数的时候会报错
		}
		HttpSession session= request.getSession();
		if (session == null || session.getAttribute(ConfigUtil.getSessionInfoName()) == null) {
			// 如果用户没有登录，则不允许上传
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("status", false);
			response.getWriter().write(JSON.toJSONString(m));
			return;
		}
		
		String datefolder = "/" + DateUtil.dateToString(new Date(), "yyyy") + "/" + DateUtil.dateToString(new Date(), "MM") + "/" + DateUtil.dateToString(new Date(), "dd");// 日期命名的文件夹
		String webRootPath = session.getServletContext().getRealPath("/");// 当前WEB环境的上层目录
		String relativePath = ConfigUtil.get("uploadFile") + fileFolder + datefolder;// 文件在服务器的相对路径
		String realPath = webRootPath + relativePath;// 文件上传到服务器的真实路径
		// System.out.println(realPath);
		

		File up = new File(realPath);
		if (!up.exists()) {
			up.mkdirs();
		}

		response.setCharacterEncoding("UTF-8");
		Integer chunk = null;// 分割块数
		Integer chunks = null;// 总分割数
		String tempFileName = null;// 上传到服务器的临时文件名
		String newFileName = null;// 最后合并后的新文件名
		BufferedOutputStream outputStream = null;
		Connection conn = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				// factory.setRepository(new File(repositoryPath));// 设置临时目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				// upload.setSizeMax(5 * 1024 * 1024);// 设置附件最大大小，超过这个大小上传会不成功
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {// 是文本域
						if (item.getFieldName().equals("name")) {
							tempFileName = item.getString();
							// System.out.println("临时文件名：" + tempFileName);
						} else if (item.getFieldName().equals("chunk")) {
							chunk = Integer.parseInt(item.getString());
							// System.out.println("当前文件块：" + (chunk + 1));
						} else if (item.getFieldName().equals("chunks")) {
							chunks = Integer.parseInt(item.getString());
							// System.out.println("文件总分块：" + chunks);
						}
					} else {// 如果是文件类型
						if (tempFileName != null) {
							String chunkName = tempFileName;
							if (chunk != null) {
								chunkName = chunk + "_" + tempFileName;
							}
							File savedFile = new File(realPath, chunkName);
							item.write(savedFile);
						}
					}
				}

				newFileName = UUID.randomUUID().toString().replace("-", "").concat(".").concat(FilenameUtils.getExtension(tempFileName));
				if (chunks == null) {// 如果不分块上传，那么只有一个名称，就是临时文件的名称
					newFileName = tempFileName;
				}
				if (chunk != null && chunk + 1 == chunks) {
					File outFile = new File(realPath, newFileName);
					outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
					// 遍历文件合并
					for (int i = 0; i < chunks; i++) {
						File tempFile = new File(realPath, i + "_" + tempFileName);
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						outputStream.write(bytes);
						outputStream.flush();
						tempFile.delete();
					}
					outputStream.flush();
					ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
					SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
					String operater =sessionInfo.getUser().getLoginName();
					DataSource ds=(DataSource)ctx.getBean("dataSource");      
					conn=ds.getConnection();  
					if (upload(conn, operater, outFile)) {
					} else {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("status", false);
						response.getWriter().write(JSON.toJSONString(m));
						return;
					}
				}
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("status", true);
				m.put("fileUrl", relativePath + "/" + newFileName);
				response.getWriter().write(JSON.toJSONString(m));
			} catch (FileUploadException e) {
				e.printStackTrace();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("status", false);
				response.getWriter().write(JSON.toJSONString(m));
			} catch (Exception e) {
				e.printStackTrace();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("status", false);
				response.getWriter().write(JSON.toJSONString(m));
			} finally {
				try {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	public boolean upload(Connection conn, String operater, File file) throws SQLException {
		try {
			InputStream is = new FileInputStream(file.getAbsolutePath());
			XSSFWorkbook wb = new XSSFWorkbook(is);  
			int scounts = wb.getNumberOfSheets();//获取表的总数  
			Statement stmt = conn.createStatement();  
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
			        	StringBuilder insertSql = new StringBuilder("INSERT INTO hpn_collections ")
			        			.append("(id,createDatetime,updateDatetime,deleteFlag,operater,commentText,latitude,longitude,name,number,pictureUrl,voiceUrl) ")
			        			.append("VALUE (UUID(),now(),now(),'0',")
			        			.append("'").append(operater).append("',")
			        			.append("'").append(collections.getCommentText()).append("',")
			        			.append("'").append(collections.getLatitude()).append("',")
			        			.append("'").append(collections.getLongitude()).append("',")
			        			.append("'").append(collections.getName()).append("',")
			        			.append("'").append(collections.getNumber()).append("',")
			        			.append("'").append(collections.getPictureUrl()).append("',")
			        			.append("'").append(collections.getVoiceUrl()).append("')");
			        	System.out.println(insertSql.toString());
			        	stmt.execute(insertSql.toString()); 
			        	//save(collections);
			        }
			}
			// 关闭
			wb.close();
	        is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<CollectionsPO> upload(String operater, File file) {
		//String filePath = "E:/SVN/doc/08_系统运维/import/典当系统用表1.xls";
		List<CollectionsPO> collectionses = new ArrayList<CollectionsPO>();
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
			        	collectionses.add(collections);
			        	//save(collections);
			        }
			}
			// 关闭
	        is.close();
	        wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return collectionses;
		}
}
