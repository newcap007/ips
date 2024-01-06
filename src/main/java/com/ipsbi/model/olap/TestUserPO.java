package com.ipsbi.model.olap;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_testUser", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TestUserPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//客户姓名
	private String paiming;
	//身份证件号码
	private String louceng;
	//出生日期
	private String keliu;
	
	@Column(name = "paiming", length = 20)
	public String getPaiming() {
		return paiming;
	}
	public void setPaiming(String paiming) {
		this.paiming = paiming;
	}

	@Column(name = "louceng", length = 20)
	public String getLouceng() {
		return louceng;
	}
	public void setLouceng(String louceng) {
		this.louceng = louceng;
	}

	@Column(name = "keliu", length = 20)
	public String getKeliu() {
		return keliu;
	}
	public void setKeliu(String keliu) {
		this.keliu = keliu;
	}
	

	
}
