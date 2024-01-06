package zone.framework.model.easyui;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 * 
 * @author linux liu
 * 
 */
public class Grid implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long total = 0L;
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	public void setRows(@SuppressWarnings("rawtypes") List rows) {
		this.rows = rows;
	}

}
