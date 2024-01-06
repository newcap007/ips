package zone.framework.service.base;

import java.util.List;

import zone.framework.model.base.OrganizationPO;
import zone.framework.service.BaseServiceI;
import zone.framework.util.base.HqlFilter;

/**
 * 机构业务
 * 
 * @author linux liu
 * 
 */
public interface OrganizationServiceI extends BaseServiceI<OrganizationPO> {

	/**
	 * 更新机构
	 */
	public void updateOrganization(OrganizationPO frmOrganization);

	/**
	 * 机构授权
	 * 
	 * @param id
	 *            机构ID
	 * @param resourceIds
	 *            资源IDS
	 */
	public void grant(String id, String resourceIds);

	/**
	 * 查找机构
	 */
	public List<OrganizationPO> findOrganizationByFilter(HqlFilter hqlFilter);

	/**
	 * 保存机构
	 * 
	 * @param data
	 *            机构信息
	 * @param id
	 *            用户ID
	 */
	public void saveOrganization(OrganizationPO frmOrganization, String userId);

}
