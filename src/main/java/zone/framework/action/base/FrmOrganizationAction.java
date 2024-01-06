package zone.framework.action.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import zone.framework.action.BaseAction;
import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.UserPO;
import zone.framework.model.easyui.Json;
import zone.framework.service.base.OrganizationServiceI;
import zone.framework.service.base.UserServiceI;
import zone.framework.util.base.ConfigUtil;
import zone.framework.util.base.HqlFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 机构
 * 
 * 访问地址：/base/frmOrganization.do
 * 
 * @author linux liu
 * 
 */
@Namespace("/base")
@Action(value = "frmOrganization")
public class FrmOrganizationAction extends BaseAction<OrganizationPO> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserServiceI userService;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(OrganizationServiceI service) {
		this.service = service;
	}

	/**
	 * 保存一个机构
	 */
	public void save() {
		Json json = new Json();
		if (data != null) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#id_S_EQ", data.getFrmOrganization().getId());
			OrganizationPO parent = service.getByFilter(hqlFilter);
			if(parent!=null){
				data.setOrgCode(parent.getOrgCode());
			}else{
				data.setOrgCode(data.getCode());
			}
			SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((OrganizationServiceI) service).saveOrganization(data, sessionInfo.getUser().getId());
			json.setSuccess(true);
		}
		writeJson(json);
	}

	/**
	 * 更新机构
	 */
	public void update() {
		Json json = new Json();
		if (!StringUtils.isBlank(data.getId())) {
			if (data.getFrmOrganization() != null && StringUtils.equals(data.getId(), data.getFrmOrganization().getId())) {
				json.setMsg("父机构不可以是自己！");
			} else {
				HqlFilter hqlFilter = new HqlFilter();
				hqlFilter.addFilter("QUERY_t#id_S_EQ", data.getFrmOrganization().getId());
				OrganizationPO parent = service.getByFilter(hqlFilter);
				if(parent!=null){
					data.setOrgCode(parent.getOrgCode());
				}else{
					data.setOrgCode(data.getCode());
				}
				((OrganizationServiceI) service).updateOrganization(data);
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}

	/**
	 * 获得机构下拉树
	 */
	public void doNotNeedSecurity_comboTree() {
		HqlFilter hqlFilter = new HqlFilter();
		writeJson(service.findByFilter(hqlFilter));
	}

	/**
	 * 机构授权
	 */
	public void grant() {
		Json json = new Json();
		((OrganizationServiceI) service).grant(id, ids);
		json.setSuccess(true);
		writeJson(json);
	}

	/**
	 * 获得当前用户能看到的所有机构树
	 */
	public void doNotNeedSecurity_getFrmOrganizationsTree() {
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		UserPO user = userService.getById(sessionInfo.getUser().getId());
		Set<OrganizationPO> organizations = user.getFrmOrganizations();
		List<OrganizationPO> l = new ArrayList<OrganizationPO>(organizations);
		Collections.sort(l, new Comparator<OrganizationPO>() {// 排序
					@Override
					public int compare(OrganizationPO o1, OrganizationPO o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		writeJson(l);
	}

	/**
	 * 获得当前用户的机构
	 */
	public void doNotNeedSecurity_getFrmOrganizationByUserId() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", id);
		List<OrganizationPO> organizations = ((OrganizationServiceI) service).findOrganizationByFilter(hqlFilter);
		writeJson(organizations);
	}

}
