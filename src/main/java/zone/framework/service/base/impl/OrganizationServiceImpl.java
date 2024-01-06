package zone.framework.service.base.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import zone.framework.dao.base.BaseDaoI;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.base.UserPO;
import zone.framework.service.base.OrganizationServiceI;
import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.BeanUtils;
import zone.framework.util.base.HqlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机构业务
 * 
 * @author linux liu
 * 
 */
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationPO> implements OrganizationServiceI {

	@Autowired
	private BaseDaoI<ResourcePO> resourceDao;

	@Autowired
	private BaseDaoI<UserPO> userDao;

	@Override
	public void updateOrganization(OrganizationPO frmOrganization) {
		if (!StringUtils.isBlank(frmOrganization.getId())) {
			OrganizationPO t = getById(frmOrganization.getId());
			OrganizationPO oldParent = t.getFrmOrganization();
			BeanUtils.copyNotNullProperties(frmOrganization, t, new String[] { "createDatetime " });
			if (frmOrganization.getFrmOrganization() != null) {// 说明要修改的节点选中了上级节点
				OrganizationPO pt = getById(frmOrganization.getFrmOrganization().getId());// 上级节点
				isParentToChild(t, pt, oldParent);// 说明要将当前节点修改到当前节点的子或者孙子下
				t.setFrmOrganization(pt);
			} else {
				t.setFrmOrganization(null);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点下
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * 
	 * @param oldParent
	 *            原上级节点
	 * @return
	 */
	private boolean isParentToChild(OrganizationPO t, OrganizationPO pt, OrganizationPO oldParent) {
		if (pt != null && pt.getFrmOrganization() != null) {
			if (StringUtils.equals(pt.getFrmOrganization().getId(), t.getId())) {
				pt.setFrmOrganization(oldParent);
				return true;
			} else {
				return isParentToChild(t, pt.getFrmOrganization(), oldParent);
			}
		}
		return false;
	}

	@Override
	public void grant(String id, String resourceIds) {
		OrganizationPO organization = getById(id);
		if (organization != null) {
			organization.setFrmResources(new HashSet<ResourcePO>());
			for (String resourceId : resourceIds.split(",")) {
				if (!StringUtils.isBlank(resourceId)) {
					ResourcePO resource = resourceDao.getById(ResourcePO.class, resourceId);
					if (resource != null) {
						organization.getFrmResources().add(resource);
					}
				}
			}
		}
	}

	@Override
	public List<OrganizationPO> findOrganizationByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from OrganizationPO t join t.frmUsers user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public void saveOrganization(OrganizationPO frmOrganization, String userId) {
		save(frmOrganization);

		UserPO user = userDao.getById(UserPO.class, userId);
		user.getFrmOrganizations().add(frmOrganization);
	}

}
