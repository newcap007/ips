package com.hpn.model.cr;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "hpn_customerOnline", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CustomerOnlinePO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	//mac码
	private String macCode;
	private CustomerPO customer;
	private Date loginDatetime;
	private Date logoutDatetime;
	private String status;// 0：在线；1.正常退出；2.异常退出

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "loginDatetime", length = 7)
	public Date getLoginDatetime() {
		if (this.loginDatetime != null)
			return this.loginDatetime;
		return new Date();
	}

	public void setLoginDatetime(Date loginDatetime) {
		this.loginDatetime = loginDatetime;
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
	
	@Column(name = "MACCode", length = 17)	
	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String macCode) {
		this.macCode = macCode;
	}
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	public CustomerPO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerPO customer) {
		this.customer = customer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logoutDatetime", length = 7)
	public Date getLogoutDatetime() {
		return logoutDatetime;
	}

	public void setLogoutDatetime(Date logoutDatetime) {
		this.logoutDatetime = logoutDatetime;
	}

	@Column(name = "status", length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
