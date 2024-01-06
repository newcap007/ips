package zone.framework.util.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import zone.framework.model.base.SessionInfo;
import zone.framework.model.base.UserPO;

public class SessionUtil {
	public static String getSessionpwd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		String pwd = "";
		if(sessionInfo != null){
			pwd = sessionInfo.getUser().getPwd();
		}
		return pwd;
	}
	
	public static UserPO getSessionUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		UserPO user = null;
		if(sessionInfo != null){
			user = sessionInfo.getUser();
		}
		return user;
	}
}
