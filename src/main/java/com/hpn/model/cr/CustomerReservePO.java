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

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_customerReserve", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CustomerReservePO extends Base implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private CustomerPO customer;
	private Date reserveDate;
	private String reserveStatus;// 1.已保存；3.审批中；5.审批拒绝；6.审批通过；
	private int reserveNum;
	private String remarks;
	
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reserveDate", length = 7)
	public Date getReserveDate() {
		if (this.reserveDate != null)
			return this.reserveDate;
		return new Date();
	}

	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	public CustomerPO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerPO customer) {
		this.customer = customer;
	}

	@Column(name = "reserveStatus", length = 2)
	public String getReserveStatus() {
		return reserveStatus;
	}

	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}

	@Column(name = "reserveNum")
	public int getReserveNum() {
		return reserveNum;
	}

	public void setReserveNum(int reserveNum) {
		this.reserveNum = reserveNum;
	}
	
	@Column(name = "remarks", length = 512)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
