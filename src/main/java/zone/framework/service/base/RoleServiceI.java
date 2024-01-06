package zone.framework.service.base;

import java.util.List;

import zone.framework.model.base.RolePO;
import zone.framework.service.BaseServiceI;
import zone.framework.util.base.HqlFilter;

/**
 * 角色业务
 * 
 * @author linux liu
 * 
 */
public interface RoleServiceI extends BaseServiceI<RolePO> {

	/**
	 * 查找角色
	 * 
	 * @param hqlFilter
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<RolePO> findRoleByFilter(HqlFilter hqlFilter, int page, int rows);

	/**
	 * 查找角色
	 */
	public List<RolePO> findRoleByFilter(HqlFilter hqlFilter);

	/**
	 * 统计角色
	 * 
	 * @param hqlFilter
	 * @return
	 */
	public Long countRoleByFilter(HqlFilter hqlFilter);

	/**
	 * 添加一个角色
	 * 
	 * @param data
	 * @param userId
	 */
	public void saveRole(RolePO FrmRole, String userId);

	/**
	 * 为角色授权
	 * 
	 * @param id
	 *            角色ID
	 * @param resourceIds
	 *            资源IDS
	 */
	public void grant(String id, String resourceIds);

}
