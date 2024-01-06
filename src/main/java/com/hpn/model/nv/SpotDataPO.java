package com.hpn.model.nv;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_spotData", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class SpotDataPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//上传手机的mac码
	private String macCode;
	
	//横坐标（纬度、X轴）
	private double latitude;
	
	//纵坐标（经度、Y轴）
	private double longitude;
	
	//方位角
	private int azimuth;
	
	//本次请求关联的图片资源
	private Set<CollectionsPO> collectionses = new HashSet<CollectionsPO>(0);
	
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "hpn_spotPictureMap", schema = "", joinColumns = { @JoinColumn(name = "spotData_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "officialPicture_id", nullable = false, updatable = false) })
	public Set<CollectionsPO> getCollectionses() {
		return collectionses;
	}

	public void setCollectionses(Set<CollectionsPO> collectionses) {
		this.collectionses = collectionses;
	}


}
