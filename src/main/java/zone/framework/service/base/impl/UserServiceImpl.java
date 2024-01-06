package zone.framework.service.base.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import zone.framework.dao.base.BaseDaoI;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.RolePO;
import zone.framework.model.base.UserPO;
import zone.framework.service.base.UserServiceI;
import zone.framework.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑
 * 
 * @author linux liu
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserPO> implements UserServiceI {

	@Autowired
	private BaseDaoI<RolePO> roleDao;

	@Autowired
	private BaseDaoI<OrganizationPO> organizationDao;

	@Override
	public void grantRole(String id, String roleIds) {
		UserPO user = getById(id);
		if (user != null) {
			user.setFrmRoles(new HashSet<RolePO>());
			for (String roleId : roleIds.split(",")) {
				if (!StringUtils.isBlank(roleId)) {
					RolePO role = roleDao.getById(RolePO.class, roleId);
					if (role != null) {
						user.getFrmRoles().add(role);
					}
				}
			}
		}
	}

	@Override
	public void grantOrganization(String id, String organizationIds) {
		UserPO user = getById(id);
		if (user != null) {
			user.setFrmOrganizations(new HashSet<OrganizationPO>());
			for (String organizationId : organizationIds.split(",")) {
				if (!StringUtils.isBlank(organizationId)) {
					OrganizationPO organization = organizationDao.getById(OrganizationPO.class, organizationId);
					if (organization != null) {
						user.getFrmOrganizations().add(organization);
					}
				}
			}
		}
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("a", k);
			params.put("b", k + 2);
			k = k + 2;
			l.add(count("select count(*) from UserPO t where HOUR(t.createDatetime )>=:a and HOUR(t.createDatetime )<:b", params));
		}
		return l;
	}

	@Override
	public Long countUserByRoleId(String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		String hql = "select count(*) from UserPO t join t.frmRoles role where role.id = :roleId";
		return count(hql, params);
	}

	@Override
	public Long countUserByNotRoleId() {
		String hql = "select count(*) from UserPO t left join t.frmRoles role where role.id is null";
		return count(hql);
	}

}
