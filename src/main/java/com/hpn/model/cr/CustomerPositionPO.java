package com.hpn.model.cr;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_customerPosition", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CustomerPositionPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;

	//数据来源类型01:APP上传；02:AP上传
	private String channelType;
	
	//上传手机的mac码
	private String macCode;
	
	//横坐标（纬度、X轴）
	private double latitude;
	
	//纵坐标（经度、Y轴）
	private double longitude;
	
	//方位角
	private int azimuth;
	
	private CusTerminalLocPO cusTerminalLoc;
	
	@Column(name = "channelType", length = 2)	
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	@Column(name = "MACCode", length = 17)	
	public String getMacCode() {
		return macCode;
	}

	@Column(name = "latitude", length = 17)
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "longitude", length = 17)
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "azimuth", length = 17)
	public int getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(int azimuth) {
		this.azimuth = azimuth;
	}

	public void setMacCode(String mACCode) {
		macCode = mACCode;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "dev_id")
	public CusTerminalLocPO getCusTerminalLoc() {
		return cusTerminalLoc;
	}

	public void setCusTerminalLoc(CusTerminalLocPO cusTerminalLoc) {
		this.cusTerminalLoc = cusTerminalLoc;
	}

}
