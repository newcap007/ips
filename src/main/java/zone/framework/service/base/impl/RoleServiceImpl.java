package zone.framework.service.base.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import zone.framework.dao.base.BaseDaoI;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.base.RolePO;
import zone.framework.model.base.UserPO;
import zone.framework.service.base.RoleServiceI;
import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.HqlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色业务逻辑
 * 
 * @author linux liu
 * 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RolePO> implements RoleServiceI {

	@Autowired
	private BaseDaoI<UserPO> userDao;
	@Autowired
	private BaseDaoI<ResourcePO> resourceDao;

	@Override
	public List<RolePO> findRoleByFilter(HqlFilter hqlFilter, int page, int rows) {
		String hql = "select distinct t from RolePO t join t.frmUsers user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
	}

	@Override
	public List<RolePO> findRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from RolePO t join t.frmUsers user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public Long countRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select count(distinct t) from RolePO t join t.frmUsers user";
		return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

	@Override
	public void saveRole(RolePO FrmRole, String userId) {
		save(FrmRole);

		UserPO user = userDao.getById(UserPO.class, userId);
		user.getFrmRoles().add(FrmRole);// 把新添加的角色与当前用户关联
	}

	@Override
	public void grant(String id, String resourceIds) {
		RolePO role = getById(id);
		if (role != null) {
			role.setFrmResources(new HashSet<ResourcePO>());
			for (String resourceId : resourceIds.split(",")) {
				if (!StringUtils.isBlank(resourceId)) {
					ResourcePO resource = resourceDao.getById(ResourcePO.class, resourceId);
					if (resource != null) {
						role.getFrmResources().add(resource);
					}
				}
			}
		}
	}

}
