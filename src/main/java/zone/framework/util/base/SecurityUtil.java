package zone.framework.util.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.base.RolePO;

/**
 * 用于前台页面判断是否有权限的工具类
 * 
 * @author linux liu
 * 
 */
public class SecurityUtil {
	private HttpSession session;

	public SecurityUtil(HttpSession session) {
		this.session = session;
	}

	/**
	 * 判断当前用户是否可以访问某资源
	 * 
	 * @param url
	 *            资源地址
	 * @return
	 */
	public boolean havePermission(String url) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		List<ResourcePO> resources = new ArrayList<ResourcePO>();
		for (RolePO role : sessionInfo.getUser().getFrmRoles()) {
			resources.addAll(role.getFrmResources());
		}
		for (OrganizationPO organization : sessionInfo.getUser().getFrmOrganizations()) {
			resources.addAll(organization.getFrmResources());
		}
		resources = new ArrayList<ResourcePO>(new HashSet<ResourcePO>(resources));// 去重(这里包含了当前用户可访问的所有资源)
		for (ResourcePO resource : resources) {
			if (StringUtils.equals(resource.getUrl(), url)) {// 如果有相同的，则代表当前用户可以访问这个资源
				return true;
			}
		}
		return false;
	}
}
