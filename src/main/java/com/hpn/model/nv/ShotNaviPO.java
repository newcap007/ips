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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;

@Entity
@Table(name = "hpn_shotNavi", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ShotNaviPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//上传手机的mac码
	private String macCode;
	//客户拍照导览的照片(二进制流)
	private String photo;
	
	//客户拍照导览的地址
	private String photoUrl;
	
	//本次请求关联的图片资源
	private Set<CollectionsPO> collectionses = new HashSet<CollectionsPO>(0);
	
	@Column(name = "MACCode", length = 17)	
	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String mACCode) {
		macCode = mACCode;
	}
	
	@Column(name = "photoUrl", length = 64)	
	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "hpn_shotPictureMap", schema = "", joinColumns = { @JoinColumn(name = "shotNavi_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "collections_id", nullable = false, updatable = false) })
	public Set<CollectionsPO> getCollectionses() {
		return collectionses;
	}

	public void setCollectionses(Set<CollectionsPO> collectionses) {
		this.collectionses = collectionses;
	}
	
	@Transient
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
