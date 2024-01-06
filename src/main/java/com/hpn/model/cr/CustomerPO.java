package com.hpn.model.cr;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_customer", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CustomerPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//电子邮件地址
	private String email;
	//编号
	private String number;
	//mac码
	private String macCode;
	//联系电话号码
	private String phoneNumber;
	//客户姓名
	private String name;
	//密码
	private String password;
	//身份证件号码
	private String idCode;
	//出生日期
	private Date birthday;
	//职业
	private String occupation;
	//性别
	private String sex;
	//客户照片
	private String photo;
	//备用电话号码
	private String secondPhoneNumber;
	
	//该游客的登录记录
	private Set<CustomerOnlinePO> onlines = new HashSet<CustomerOnlinePO>(0);

	@Column(name = "name", length = 64)
	public String getName() {
		return name;
	}
	
	@Column(name = "idCode", length = 20)
	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return sex;
	}
	
	@Column(name = "occupation", length = 16)
	public String getOccupation() {
		return occupation;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", length = 8)
	public Date getBirthday() {
		return birthday;
	}

	@Column(name = "photo", length = 200)
	public String getPhoto() {
		return this.photo;
	}

	@Column(name = "phoneNumber", length = 16)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Column(name = "secondPhoneNumber", length = 16)
	public String getSecondPhoneNumber() {
		return secondPhoneNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSecondPhoneNumber(String secondPhoneNumber) {
		this.secondPhoneNumber = secondPhoneNumber;
	}

	@Column(name = "number", length = 16)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "email", length = 32)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<CustomerOnlinePO> getOnlines() {
		return onlines;
	}

	public void setOnlines(Set<CustomerOnlinePO> onlines) {
		this.onlines = onlines;
	}

	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String macCode) {
		this.macCode = macCode;
	}
	
}
