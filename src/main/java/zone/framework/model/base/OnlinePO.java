package zone.framework.model.base;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "frm_online", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OnlinePO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String loginName;
	private String ip;
	private Date createDatetime;
	private String type;// 1.登录0.注销

	public OnlinePO() {
	}

	public OnlinePO(String id) {
		this.id = id;
	}

	public OnlinePO(String id, String loginName, String ip, Date createDatetime, String type) {
		this.id = id;
		this.loginName = loginName;
		this.ip = ip;
		this.createDatetime = createDatetime;
		this.type = type;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	public String getId() {
		if (!StringUtils.isBlank(this.id)) {
			return this.id;
		}
		return UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "loginName", length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createDatetime", length = 7)
	public Date getCreateDatetime() {
		if (this.createDatetime != null)
			return this.createDatetime;
		return new Date();
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	@Column(name = "type", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
