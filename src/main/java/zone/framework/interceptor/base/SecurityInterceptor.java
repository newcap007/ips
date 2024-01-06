package zone.framework.interceptor.base;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.OrganizationPO;
import zone.framework.model.base.ResourcePO;
import zone.framework.model.base.RolePO;
import zone.framework.util.base.ConfigUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器
 * 
 * @author linux liu
 * 
 */
public class SecurityInterceptor extends MethodFilterInterceptor {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		//ActionContext actionContext = actionInvocation.getInvocationContext();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getSessionInfoName());
		String servletPath = ServletActionContext.getRequest().getServletPath();
		String url = ServletActionContext.getRequest().getServletPath();
		logger.info(new StringBuilder("进入session拦截器->访问路径为[").append(url).append("]").toString() );
		if(url.contains("/app/")){
			return actionInvocation.invoke();
		}
		servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀 *.do *.action之类的
		Set<RolePO> roles = sessionInfo.getUser().getFrmRoles();
		for (RolePO role : roles) {
			for (ResourcePO resource : role.getFrmResources()) {
				Logger.getLogger(getClass()).info(resource.getUrl());
				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
					return actionInvocation.invoke();
				}
			}
		}
		Set<OrganizationPO> organizations = sessionInfo.getUser().getFrmOrganizations();
		for (OrganizationPO organization : organizations) {
			for (ResourcePO resource : organization.getFrmResources()) {
				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
					return actionInvocation.invoke();
				}
			}
		}
		String errMsg = "您没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
		logger.info(errMsg);
		ServletActionContext.getRequest().setAttribute("msg", errMsg);
		return "noSecurity";
	}

}
