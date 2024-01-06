package zone.framework.cxf.demo.impl;

import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import zone.framework.cxf.demo.WebServiceDemoI;
import zone.framework.model.base.UserPO;
import zone.framework.service.base.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * WebService接口实现类
 * 
 * @author linux liu
 * 
 */
@WebService(endpointInterface = "zone.framework.cxf.demo.WebServiceDemoI", serviceName = "webServiceDemo")
public class WebServiceDemoImpl implements WebServiceDemoI {

	@Autowired
	private UserServiceI userService;

	@Override
	public String helloWs(String name) {
		if (StringUtils.isBlank(name)) {
			name = "SunYu";
		}
		String str = "hello[" + name.trim() + "]WebService test ok!";
		System.out.println(str);
		return str;
	}

	@Override
	public UserPO getUser(String id) {
		if (StringUtils.isBlank(id)) {
			id = "0";
		}
		UserPO user = userService.getById(id.trim());
		UserPO u = new UserPO();
		u.setName(user.getName());
		u.setAge(user.getAge());
		u.setCreateDatetime(user.getCreateDatetime());
		u.setUpdateDatetime(user.getUpdateDatetime());
		u.setId(user.getId());
		u.setLoginName(user.getLoginName());
		u.setSex(user.getSex());
		u.setPhoto(user.getPhoto());
		return u;
	}

}
