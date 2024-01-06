package com.hpn.model.nv;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import zone.framework.model.base.Base;
import zone.framework.model.base.OrganizationPO;

@Entity
@Table(name = "hpn_indoorMap", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class IndoorMapPO extends Base implements Serializable{

	/**
	 * 
	 * 继成父类的 deleteFlag 作为作废标识 0：正常；1：作废
	 */
	private static final long serialVersionUID = 1L;
	//地图名称  如：故宫博物院午门一层
	private String name;
	//地图编码 如gugong_wumen_1
	private String number;
	//地图资源地址
	private String mapUrl;
	//地图文字介绍
	private String comment;
	//地图所属组织（博物馆）
	private OrganizationPO organization;
	//该地图关联的藏品
	private Set<CollectionsPO> collectionses = new HashSet<CollectionsPO>(0);
	
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
	@Column(name = "mapUrl", length = 64)
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	@Column(name = "comment", length = 1024)
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "organization_id")//注释本表中指向另一个表的外键。
	public OrganizationPO getOrganization() {
		return organization;
	}
	public void setOrganization(OrganizationPO organization) {
		this.organization = organization;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "indoorMap")
	public Set<CollectionsPO> getCollectionses() {
		return collectionses;
	}
	public void setCollectionses(Set<CollectionsPO> collectionses) {
		this.collectionses = collectionses;
	}
}
