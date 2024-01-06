package zone.framework.service.base;

import java.util.List;

import zone.framework.model.base.ResourcePO;
import zone.framework.service.BaseServiceI;
import zone.framework.util.base.HqlFilter;

/**
 * 资源业务逻辑
 * 
 * @author linux liu
 * 
 */
public interface ResourceServiceI extends BaseServiceI<ResourcePO> {

	/**
	 * 获得资源树，或者combotree(只查找菜单类型的节点)
	 * 
	 * @return
	 */
	public List<ResourcePO> getMainMenu(HqlFilter hqlFilter);

	/**
	 * 获得资源treeGrid
	 * 
	 * @return
	 */
	public List<ResourcePO> resourceTreeGrid(HqlFilter hqlFilter);

	/**
	 * 更新资源
	 */
	public void updateResource(ResourcePO syresource);

	/**
	 * 保存一个资源
	 * 
	 * @param syresource
	 * @param userId
	 * @return
	 */
	public void saveResource(ResourcePO syresource, String userId);

	/**
	 * 查找符合条件的资源
	 */
	public List<ResourcePO> findResourceByFilter(HqlFilter hqlFilter);

}
