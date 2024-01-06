package zone.framework.action.base;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import zone.framework.action.BaseAction;
import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.RolePO;
import zone.framework.model.base.UserPO;
import zone.framework.model.easyui.Grid;
import zone.framework.model.easyui.Json;
import zone.framework.service.base.UserServiceI;
import zone.framework.util.base.BeanUtils;
import zone.framework.util.base.ConfigUtil;
import zone.framework.util.base.HqlFilter;
import zone.framework.util.base.IpUtil;
import zone.framework.util.base.MD5Util;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户
 * 
 * action访问地址是/base/frmUser.do
 * 
 * @author linux liu
 * 
 */
@Namespace("/base")
@Action(value = "frmUser")
public class FrmUserAction extends BaseAction<UserPO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(UserServiceI service) {
		this.service = service;
	}

	/**
	 * 注销系统
	 */
	public void doNotNeedSessionAndSecurity_logout() {
		if (getSession() != null) {
			getSession().invalidate();
		}
		Json j = new Json();
		j.setSuccess(true);
		writeJson(j);
	}

	/**
	 * 注册
	 */
	synchronized public void doNotNeedSessionAndSecurity_reg() {
		Json json = new Json();
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());
		UserPO user = service.getByFilter(hqlFilter);
		if (user != null) {
			json.setMsg("用户名已存在！");
			writeJson(json);
		} else {
			UserPO u = new UserPO();
			u.setLoginName(data.getLoginName());
			u.setPwd(MD5Util.md5(data.getPwd()));
			service.save(u);
			doNotNeedSessionAndSecurity_login();
		}
	}

	/**
	 * 登录
	 */
	public void doNotNeedSessionAndSecurity_login() {
		HqlFilter hqlFilter = new HqlFilter();
		System.out.println(data.getLoginName());
		System.out.println(data.getPwd());
		hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());
		hqlFilter.addFilter("QUERY_t#pwd_S_EQ", MD5Util.md5(data.getPwd()));
		UserPO user = service.getByFilter(hqlFilter);
		Json json = new Json();
		if (user != null) {
			json.setSuccess(true);
			SessionInfo sessionInfo = new SessionInfo();
			Hibernate.initialize(user.getFrmRoles());
			Hibernate.initialize(user.getFrmOrganizations());
			for (RolePO role : user.getFrmRoles()) {
				Hibernate.initialize(role.getFrmResources());
			}
			for (OrganizationPO organization : user.getFrmOrganizations()) {
				Hibernate.initialize(organization.getFrmResources());
			}
			user.setIp(IpUtil.getIpAddr(getRequest()));
			sessionInfo.setUser(user);
			getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		} else {
			json.setMsg("用户名或密码错误！");
		}
		writeJson(json);
	}

	/**
	 * 修改自己的密码
	 */
	public void doNotNeedSecurity_updateCurrentPwd() {
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		Json json = new Json();
		UserPO user = service.getById(sessionInfo.getUser().getId());
		user.setPwd(MD5Util.md5(data.getPwd()));
		user.setUpdateDatetime(new Date());
		service.update(user);
		json.setSuccess(true);
		writeJson(json);
	}

	/**
	 * 修改用户角色
	 */
	public void grantRole() {
		Json json = new Json();
		((UserServiceI) service).grantRole(id, ids);
		json.setSuccess(true);
		writeJson(json);
	}

	/**
	 * 修改用户机构
	 */
	public void grantOrganization() {
		Json json = new Json();
		((UserServiceI) service).grantOrganization(id, ids);
		json.setSuccess(true);
		writeJson(json);
	}

	/**
	 * 统计用户注册时间图表
	 */
	public void doNotNeedSecurity_userCreateDatetimeChart() {
		writeJson(((UserServiceI) service).userCreateDatetimeChart());
	}

	/**
	 * 新建一个用户
	 */
	synchronized public void save() {
		Json json = new Json();
		if (data != null) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());
			UserPO user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("新建用户失败，用户名已存在！");
			} else {
				data.setPwd(MD5Util.md5("123456"));
				service.save(data);
				json.setMsg("新建用户成功！默认密码：123456");
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}

	/**
	 * 更新一个用户
	 */
	synchronized public void update() {
		Json json = new Json();
		json.setMsg("更新失败！");
		if (data != null && !StringUtils.isBlank(data.getId())) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#id_S_NE", data.getId());
			hqlFilter.addFilter("QUERY_t#loginName_S_EQ", data.getLoginName());
			UserPO user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("更新用户失败，用户名已存在！");
			} else {
				UserPO t = service.getById(data.getId());
				BeanUtils.copyNotNullProperties(data, t, new String[] { "createDatetime " });
				service.update(t);
				json.setSuccess(true);
				json.setMsg("更新成功！");
			}
		}
		writeJson(json);
	}

	/**
	 * 用户登录页面的自动补全
	 */
	public void doNotNeedSessionAndSecurity_loginNameComboBox() {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("QUERY_t#loginName_S_LK", "%%" + StringUtils.defaultString(q) + "%%");
		hqlFilter.addSort("t.loginName");
		hqlFilter.addOrder("asc");
		writeJsonByIncludesProperties(service.findByFilter(hqlFilter, 1, 10), new String[] { "loginName" });
	}

	/**
	 * 用户登录页面的grid自动补全
	 */
	public void doNotNeedSessionAndSecurity_loginNameComboGrid() {
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_t#loginName_S_LK", "%%" + StringUtils.defaultString(q) + "%%");
		grid.setTotal(service.countByFilter(hqlFilter));
		grid.setRows(service.findByFilter(hqlFilter, page, rows));
		writeJson(grid);
	}

}
