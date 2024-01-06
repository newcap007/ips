package zone.framework.service.base.impl;

import java.util.List;

import zone.framework.model.base.ResourceTypePO;
import zone.framework.service.base.ResourceTypeServiceI;
import zone.framework.service.impl.BaseServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 资源类型业务逻辑
 * 
 * @author linux liu
 * 
 */
@Service
public class ResourceTypeServiceImpl extends BaseServiceImpl<ResourceTypePO> implements ResourceTypeServiceI {

	/**
	 * 为列表添加了缓存，查询一次过后，只要不重启服务，缓存一直存在，不需要再查询数据库了，节省了一些资源
	 * 
	 * 在ehcache.xml里面需要有对应的value
	 * 
	 * <cache name="FrmResourceTypeServiceCache"
	 * 
	 * key是自己设定的一个ID，用来标识缓存
	 */
	@Override
	@Cacheable(value = "FrmResourceTypeServiceCache", key = "'FrmResourceTypeList'")
	public List<ResourceTypePO> findResourceType() {
		return find();
	}

}
