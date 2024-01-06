package zone.framework.converter.base;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.springframework.web.util.HtmlUtils;

/**
 * 用于防止XSS攻击
 * 
 * @author linux liu
 * 
 */
public class EscapeConverter extends StrutsTypeConverter {

	@Override
	public String convertToString(@SuppressWarnings("rawtypes") Map context, Object o) {
		if (o != null) {
			if (o instanceof String[]) {
				String[] str = (String[]) o;
				if (str != null && !StringUtils.isBlank(str[0])) {
					return HtmlUtils.htmlEscape(str[0]);
				}
			} else if (o instanceof String) {
				String str = (String) o;
				if (!StringUtils.isBlank(str)) {
					return HtmlUtils.htmlEscape(str);
				}
			}
		}
		return "";
	}

	@Override
	public Object convertFromString(@SuppressWarnings("rawtypes") Map context, String[] values, @SuppressWarnings("rawtypes") Class toClass) {
		return values;
	}

}
