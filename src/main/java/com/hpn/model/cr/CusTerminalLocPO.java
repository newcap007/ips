package com.hpn.model.cr;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tab_terminal_loc", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class CusTerminalLocPO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int devId;
	private String muMac;
	private double X;
	private double Y;
	private double Z;
	private int accuracyLevel;
	private int floor;
	private String shopname;
	private int isshop;
	private int isshopflow;
	private String apMac;
	private String rssi;
	private int isAssociated;
	private String frameControl;
	private int channel;
	private Date recvTime;

	@Id
	@Column(name = "dev_id", unique = true, nullable = false, length = 10)
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	@Column(name = "muMac", length = 18)
	public String getMuMac() {
		return muMac;
	}
	public void setMuMac(String muMac) {
		this.muMac = muMac;
	}
	@Column(name = "X", length = 8)
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	@Column(name = "Y", length = 8)
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	@Column(name = "Z", length = 8)
	public double getZ() {
		return Z;
	}
	public void setZ(double z) {
		Z = z;
	}
	@Column(name = "AccuracyLevel", length = 2)
	public int getAccuracyLevel() {
		return accuracyLevel;
	}
	public void setAccuracyLevel(int accuracyLevel) {
		this.accuracyLevel = accuracyLevel;
	}
	@Column(name = "floor", length = 2)
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	@Column(name = "shopname", length = 18)
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	@Column(name = "isshop", length = 2)
	public int getIsshop() {
		return isshop;
	}
	public void setIsshop(int isshop) {
		this.isshop = isshop;
	}
	@Column(name = "isshopflow", length = 2)
	public int getIsshopflow() {
		return isshopflow;
	}
	public void setIsshopflow(int isshopflow) {
		this.isshopflow = isshopflow;
	}
	@Column(name = "apMac", length = 254)
	public String getApMac() {
		return apMac;
	}
	public void setApMac(String apMac) {
		this.apMac = apMac;
	}
	@Column(name = "rssi", length = 254)
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	@Column(name = "isAssociated", length = 2)
	public int getIsAssociated() {
		return isAssociated;
	}
	public void setIsAssociated(int isAssociated) {
		this.isAssociated = isAssociated;
	}
	@Column(name = "frameControl", length = 2)
	public String getFrameControl() {
		return frameControl;
	}
	public void setFrameControl(String frameControl) {
		this.frameControl = frameControl;
	}
	@Column(name = "channel", length = 5)
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recvTime", length = 10)
	public Date getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(Date recvTime) {
		this.recvTime = recvTime;
	}
	
	
}
