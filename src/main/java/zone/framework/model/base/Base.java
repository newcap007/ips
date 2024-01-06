package zone.framework.model.base;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
@MappedSuperclass
public class Base {

	private String id;
	private String operater;
	private String deleteFlag="0";
	private Date createDatetime;
	private Date updateDatetime;
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDatetime", length = 7)
	public Date getCreateDatetime() {
		if (this.createDatetime != null)
			return this.createDatetime;
		return new Date();
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updateDatetime", length = 7)
	public Date getUpdateDatetime() {
		if (this.updateDatetime != null)
			return this.updateDatetime;
		return new Date();
	}
	@Column(name = "deleteFlag", length = 1)
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "operater",length = 20)
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	
}
