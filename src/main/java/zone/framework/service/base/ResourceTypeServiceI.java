package zone.framework.service.base;

import java.util.List;

import zone.framework.model.base.ResourceTypePO;
import zone.framework.service.BaseServiceI;

/**
 * 资源类型业务
 * 
 * @author linux liu
 * 
 */
public interface ResourceTypeServiceI extends BaseServiceI<ResourceTypePO> {

	/**
	 * 获取所有资源类型
	 */
	public List<ResourceTypePO> findResourceType();

}
