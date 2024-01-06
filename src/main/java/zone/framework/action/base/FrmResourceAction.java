package zone.framework.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import zone.framework.action.BaseAction;
import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.easyui.Json;
import zone.framework.model.easyui.Tree;
import zone.framework.service.base.ResourceServiceI;
import zone.framework.util.base.BeanUtils;
import zone.framework.util.base.ConfigUtil;
import zone.framework.util.base.HqlFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 资源
 * 
 * @author linux liu
 * 
 */
@Namespace("/base")
@Action(value = "frmResource")
public class FrmResourceAction extends BaseAction<ResourcePO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(ResourceServiceI service) {
		this.service = service;
	}

	/**
	 * 更新资源
	 */
	public void update() {
		Json json = new Json();
		if (!StringUtils.isBlank(data.getId())) {
			if (data.getFrmResource() != null && StringUtils.equals(data.getId(), data.getFrmResource().getId())) {
				json.setMsg("父资源不可以是自己！");
			} else {
				((ResourceServiceI) service).updateResource(data);
				json.setSuccess(true);
			}
		}
		writeJson(json);
	}

	/**
	 * 获得主菜单tree，也用于获得上级资源菜单combotree
	 */
	public void doNotNeedSecurity_getMainMenu() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		hqlFilter.addFilter("QUERY_t#frmResourceType#id_S_EQ", "60366688-0001-0001-0011-8888rstype00");// 0就是只查菜单
		List<ResourcePO> resources = ((ResourceServiceI) service).getMainMenu(hqlFilter);
		List<Tree> tree = new ArrayList<Tree>();
		for (ResourcePO resource : resources) {
			Tree node = new Tree();
			BeanUtils.copyNotNullProperties(resource, node);
			node.setText(resource.getName());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			attributes.put("target", resource.getTarget());
			node.setAttributes(attributes);
			tree.add(node);
		}
		writeJson(tree);
	}

	/**
	 * 获得资源treeGrid
	 */
	public void treeGrid() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		writeJson(((ResourceServiceI) service).resourceTreeGrid(hqlFilter));
	}

	/**
	 * 获得角色的资源列表
	 */
	public void doNotNeedSecurity_getRoleResources() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_role#id_S_EQ", id);
		writeJson(((ResourceServiceI) service).findResourceByFilter(hqlFilter));
	}

	/**
	 * 获得机构的资源列表
	 */
	public void doNotNeedSecurity_getOrganizationResources() {
		HqlFilter hqlFilter = new HqlFilter(getRequest());
		hqlFilter.addFilter("QUERY_organization#id_S_EQ", id);
		writeJson(((ResourceServiceI) service).findResourceByFilter(hqlFilter));
	}

	/**
	 * 获得资源树
	 */
	public void doNotNeedSecurity_getResourcesTree() {
		treeGrid();
	}

	/**
	 * 保存一个资源
	 */
	public void save() {
		Json json = new Json();
		if (data != null) {
			SessionInfo sessionInfo = (SessionInfo) getSession().getAttribute(ConfigUtil.getSessionInfoName());
			((ResourceServiceI) service).saveResource(data, sessionInfo.getUser().getId());
			json.setSuccess(true);
		}
		writeJson(json);
	}

}
