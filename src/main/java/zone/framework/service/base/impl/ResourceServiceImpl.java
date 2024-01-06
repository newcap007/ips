package zone.framework.service.base.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import zone.framework.dao.base.BaseDaoI;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.base.RolePO;
import zone.framework.model.base.UserPO;
import zone.framework.service.base.ResourceServiceI;
import zone.framework.service.impl.BaseServiceImpl;
import zone.framework.util.base.BeanUtils;
import zone.framework.util.base.HqlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源业务逻辑
 * 
 * @author linux liu
 * 
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourcePO> implements ResourceServiceI {

	@Autowired
	private BaseDaoI<UserPO> userDao;

	/**
	 * 由于角色与资源关联，机构也与资源关联，所以查询用户能看到的资源菜单应该查询两次，最后合并到一起。
	 */
	@Override
	public List<ResourcePO> getMainMenu(HqlFilter hqlFilter) {
		List<ResourcePO> l = new ArrayList<ResourcePO>();
		String hql = "select distinct t from ResourcePO t join t.frmRoles role join role.frmUsers user";
		List<ResourcePO> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
		hql = "select distinct t from ResourcePO t join t.frmOrganizations organization join organization.frmUsers user";
		List<ResourcePO> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_organization);
		l = new ArrayList<ResourcePO>(new HashSet<ResourcePO>(l));// 去重
		Collections.sort(l, new Comparator<ResourcePO>() {// 排序
					@Override
					public int compare(ResourcePO o1, ResourcePO o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public List<ResourcePO> resourceTreeGrid(HqlFilter hqlFilter) {
		List<ResourcePO> l = new ArrayList<ResourcePO>();
		String hql = "select distinct t from ResourcePO t join t.frmRoles role join role.frmUsers user";
		List<ResourcePO> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
		hql = "select distinct t from ResourcePO t join t.frmOrganizations organization join organization.frmUsers user";
		List<ResourcePO> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_organization);
		l = new ArrayList<ResourcePO>(new HashSet<ResourcePO>(l));// 去重
		Collections.sort(l, new Comparator<ResourcePO>() {// 排序
					@Override
					public int compare(ResourcePO o1, ResourcePO o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public void updateResource(ResourcePO syresource) {
		if (!StringUtils.isBlank(syresource.getId())) {
			ResourcePO t = getById(syresource.getId());
			ResourcePO oldParent = t.getFrmResource();
			BeanUtils.copyNotNullProperties(syresource, t, new String[] { "createDatetime " });
			if (syresource.getFrmResource() != null) {// 说明要修改的节点选中了上级节点
				ResourcePO pt = getById(syresource.getFrmResource().getId());// 上级节点
				isParentToChild(t, pt, oldParent);// 说明要将当前节点修改到当前节点的子或者孙子下
				t.setFrmResource(pt);
			} else {
				t.setFrmResource(null);
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
	private boolean isParentToChild(ResourcePO t, ResourcePO pt, ResourcePO oldParent) {
		if (pt != null && pt.getFrmResource() != null) {
			if (StringUtils.equals(pt.getFrmResource().getId(), t.getId())) {
				pt.setFrmResource(oldParent);
				return true;
			} else {
				return isParentToChild(t, pt.getFrmResource(), oldParent);
			}
		}
		return false;
	}

	/**
	 * 由于新添加的资源，当前用户的角色或者机构并没有访问此资源的权限，所以这个地方重写save方法，将新添加的资源放到用户的第一个角色里面或者第一个机构里面
	 */
	@Override
	public void saveResource(ResourcePO syresource, String userId) {
		save(syresource);

		UserPO user = userDao.getById(UserPO.class, userId);
		Set<RolePO> roles = user.getFrmRoles();
		if (roles != null && !roles.isEmpty()) {// 如果用户有角色，就将新资源放到用户的第一个角色里面
			List<RolePO> l = new ArrayList<RolePO>();
			l.addAll(roles);
			Collections.sort(l, new Comparator<RolePO>() {
				@Override
				public int compare(RolePO o1, RolePO o2) {
					if (o1.getCreateDatetime().getTime() > o2.getCreateDatetime().getTime()) {
						return 1;
					}
					if (o1.getCreateDatetime().getTime() < o2.getCreateDatetime().getTime()) {
						return -1;
					}
					return 0;
				}
			});
			l.get(0).getFrmResources().add(syresource);
		} else {// 如果用户没有角色
			Set<OrganizationPO> organizations = user.getFrmOrganizations();
			if (organizations != null && !organizations.isEmpty()) {// 如果用户没有角色，但是有机构，那就将新资源放到第一个机构里面
				List<OrganizationPO> l = new ArrayList<OrganizationPO>();
				l.addAll(organizations);
				Collections.sort(l, new Comparator<OrganizationPO>() {
					@Override
					public int compare(OrganizationPO o1, OrganizationPO o2) {
						if (o1.getCreateDatetime().getTime() > o2.getCreateDatetime().getTime()) {
							return 1;
						}
						if (o1.getCreateDatetime().getTime() < o2.getCreateDatetime().getTime()) {
							return -1;
						}
						return 0;
					}
				});
				l.get(0).getFrmResources().add(syresource);
			}
		}
	}

	@Override
	public List<ResourcePO> findResourceByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from ResourcePO t left join t.frmRoles role left join t.frmOrganizations organization";
		return find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

}
