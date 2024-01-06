package zone.framework.model.co;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "frm_serialNumber", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SerialNumberPO extends Base implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//编码名称
	private String name;
	//元数据-关联表名
	private String metaTable;
	//元数据-关联列名
	private String metaColumn;
	//元数据-关联特征
	private String relatTeature;
	//元数据-关联补充特征
	private String relatAddTeature;
	//当前可使用的流水号
	private int serialNumber;
	
	@Column(name = "name", length = 40)
	public String getName() {
		return name;
	}
	
	@Column(name = "metaTable", length = 30)
	public String getMetaTable() {
		return metaTable;
	}
	
	@Column(name = "metaColumn", length = 30)
	public String getMetaColumn() {
		return metaColumn;
	}
	
	@Column(name = "relatTeature", length = 2)
	public String getRelatTeature() {
		return relatTeature;
	}
	
	@Column(name = "relatAddTeature", length = 2)
	public String getRelatAddTeature() {
		return relatAddTeature;
	}
	
	@Column(name = "serialNumber", length = 10)
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMetaTable(String metaTable) {
		this.metaTable = metaTable;
	}
	public void setMetaColumn(String metaColumn) {
		this.metaColumn = metaColumn;
	}
	public void setRelatTeature(String relatTeature) {
		this.relatTeature = relatTeature;
	}
	public void setRelatAddTeature(String relatAddTeature) {
		this.relatAddTeature = relatAddTeature;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
