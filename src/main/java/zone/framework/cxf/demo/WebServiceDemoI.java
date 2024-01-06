package zone.framework.cxf.demo;

import javax.jws.WebParam;
import javax.jws.WebService;

import zone.framework.model.base.UserPO;

/**
 * WebService接口定义
 * 
 * @author linux liu
 * 
 */
@WebService
public interface WebServiceDemoI {

	public String helloWs(@WebParam(name = "userName") String name);

	public UserPO getUser(@WebParam(name = "userId") String id);

}
