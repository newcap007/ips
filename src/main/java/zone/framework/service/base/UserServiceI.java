package zone.framework.service.base;

import java.util.List;

import zone.framework.model.base.UserPO;
import zone.framework.service.BaseServiceI;

/**
 * 用户业务
 * 
 * @author linux liu
 * 
 */
public interface UserServiceI extends BaseServiceI<UserPO> {

	/**
	 * 修改用户角色
	 * 
	 * @param id
	 *            用户ID
	 * @param roleIds
	 *            角色IDS
	 */
	public void grantRole(String id, String roleIds);

	/**
	 * 修改用户机构
	 * 
	 * @param id
	 *            用户ID
	 * @param organizationIds
	 *            机构IDS
	 */
	public void grantOrganization(String id, String organizationIds);

	/**
	 * 统计用户注册时间图表
	 * 
	 * @return
	 */
	public List<Long> userCreateDatetimeChart();

	/**
	 * 统计?角色的用户
	 * 
	 * @param roleId
	 * @return
	 */
	public Long countUserByRoleId(String roleId);

	/**
	 * 统计没有角色的用户数量
	 * 
	 * @return
	 */
	public Long countUserByNotRoleId();

}
