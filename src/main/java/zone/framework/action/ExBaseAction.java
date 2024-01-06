package zone.framework.action;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;

import zone.framework.model.co.SerialNumberPO;
import zone.framework.model.easyui.Grid;
import zone.framework.service.co.SerialNumberServiceI;
import zone.framework.util.base.FastjsonFilter;
import zone.framework.util.base.HqlFilter;

/**
 * 基础ACTION,其他ACTION继承此ACTION来获得writeJson和ActionSupport的功能
 * 
 * 基本的CRUD已实现，子类继承BaseAction的时候，提供setService方法即可
 * 
 * 注解@Action后，访问地址就是命名空间+类名(全小写，并且不包括Action后缀)，本action的访问地址就是/base.do
 * 
 * @author linux liu
 * 
 */
@ParentPackage("SyPackage")
@Namespace("/")
@Action
public class ExBaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ExBaseAction.class);
	protected Grid grid = new Grid();
	protected int page = 1;// 当前页
	protected int rows = 10;// 每页显示记录数
	protected String sort;// 排序字段
	protected String order = "asc";// asc/desc
	protected String q;// easyui的combo和其子类过滤时使用

	protected String id;// 主键
	protected String ids;// 主键集合，逗号分割
	
	@Autowired
	protected SerialNumberServiceI serialNumberService;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 产生一个新的序列号
	 * 
	 * @param relatTeature
	 *            关联特性，如果不需要请输入null
	 * @param relatAddTeature
	 *            附加的关联特性，如果不需要请输入null
	 */
	public int findSerialNumber(String relatTeature, String relatAddTeature) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("QUERY_t#metaTable_S_EQ", "IFR_LOANTICKET");
		hqlFilter.addFilter("QUERY_t#metaColumn_S_EQ", "number");
		if (relatTeature != null) {
			hqlFilter.addFilter("QUERY_t#relatTeature_S_EQ",
					String.valueOf(relatTeature));
		}
		if (relatAddTeature != null) {
			hqlFilter.addFilter("QUERY_t#relatAddTeature_S_EQ",
					String.valueOf(relatAddTeature));
		}
		SerialNumberPO serialNumber = serialNumberService.getByFilter(hqlFilter);
		if (serialNumber == null) {
			serialNumber = new SerialNumberPO();
			serialNumber.setMetaTable("IFR_LOANTICKET");
			serialNumber.setMetaColumn("number");
			if (relatTeature != null) {
				serialNumber.setRelatTeature(relatTeature);
			}
			if (relatAddTeature != null) {
				serialNumber.setRelatTeature(relatAddTeature);
			}
			serialNumberService.save(serialNumber);
		}
		int number = serialNumber.getSerialNumber() + 1;
		serialNumber.setSerialNumber(number);
		serialNumberService.update(serialNumber);
		return number;
	}
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
			logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			String json;
			String User_Agent = getRequest().getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			}
			logger.info("转换后的JSON字符串：" + json);
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().write(json);
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object) {
		writeJsonByFilter(object, null, null);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 */
	public void writeJsonByIncludesProperties(Object object, String[] includesProperties) {
		writeJsonByFilter(object, includesProperties, null);
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByExcludesProperties(Object object, String[] excludesProperties) {
		writeJsonByFilter(object, null, excludesProperties);
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

}
