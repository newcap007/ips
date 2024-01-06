package zone.framework.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import zone.framework.action.BaseAction;
import zone.framework.model.base.ResourceTypePO;
import zone.framework.service.base.ResourceTypeServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 资源类型
 * 
 * @author linux liu
 * 
 */
@Namespace("/base")
@Action(value = "frmResourceType")
public class FrmResourceTypeAction extends BaseAction<ResourceTypePO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 注入业务逻辑，使当前action调用service.xxx的时候，直接是调用基础业务逻辑
	 * 
	 * 如果想调用自己特有的服务方法时，请使用((TServiceI) service).methodName()这种形式强转类型调用
	 * 
	 * @param service
	 */
	@Autowired
	public void setService(ResourceTypeServiceI service) {
		this.service = service;
	}

	/**
	 * 获得资源类型combobox
	 */
	public void doNotNeedSecurity_combobox() {
		writeJson(((ResourceTypeServiceI) service).findResourceType());
	}

}
