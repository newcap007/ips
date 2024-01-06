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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;
import zone.framework.model.base.OrganizationPO;

@Entity
@Table(name = "hpn_collections", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CollectionsPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//藏品名称
	private String name;
	//藏品编码
	private String number;
	//藏品资源地址
	private String pictureUrl;
	//藏品文字介绍
	private String commentText;
	//藏品资源地址
	private String voiceUrl;
	//横坐标（X轴、纬度）
	private double latitude;
	//纵坐标（Y轴、经度）
	private double longitude;
	//上传excel文件的临时地址，不保存到数据库
	private String fileUrl;
	
	//该藏品资源被谁请求过
	private Set<SpotDataPO> spotDatas = new HashSet<SpotDataPO>(0);
	
	//该藏品资源被谁请求过
	private Set<ShotNaviPO> shotNavis = new HashSet<ShotNaviPO>(0);
	
	//藏品在哪个地图上
	private IndoorMapPO indoorMap;
	
	@Column(name = "name", length = 64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Column(name = "pictureUrl", length = 64)
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	@Column(name = "commentText", length = 1024)
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	@Column(name = "voiceUrl", length = 64)
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	@Column(name = "latitude", length = 64)
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	@Column(name = "longitude", length = 64)
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "hpn_spotPictureMap", schema = "", joinColumns = { @JoinColumn(name = "officialPicture_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "spotData_id", nullable = false, updatable = false) })
	public Set<SpotDataPO> getSpotDatas() {
		return spotDatas;
	}
	public void setSpotDatas(Set<SpotDataPO> spotDatas) {
		this.spotDatas = spotDatas;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "hpn_shotPictureMap", schema = "", joinColumns = { @JoinColumn(name = "collections_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "shotNavi_id", nullable = false, updatable = false) })
	public Set<ShotNaviPO> getShotNavis() {
		return shotNavis;
	}
	public void setShotNavis(Set<ShotNaviPO> shotNavis) {
		this.shotNavis = shotNavis;
	}
	@Transient
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indoorMap_id")
	public IndoorMapPO getIndoorMap() {
		return indoorMap;
	}
	public void setIndoorMap(IndoorMapPO indoorMap) {
		this.indoorMap = indoorMap;
	}
	
}
