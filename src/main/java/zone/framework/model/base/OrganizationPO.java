package zone.framework.model.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "frm_organization", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class OrganizationPO extends Base implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String pid;// 虚拟属性，用于获得当前机构的父机构ID

	private String name;
	private String address;
	private String code;
	private String iconCls;
	private Integer seq;
	//业务组织编码
	private String orgCode;
	private OrganizationPO frmOrganization;
	private Set<OrganizationPO> frmOrganizations = new HashSet<OrganizationPO>(0);
	private Set<UserPO> frmUsers = new HashSet<UserPO>(0);
	private Set<ResourcePO> frmResources = new HashSet<ResourcePO>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id")
	public OrganizationPO getFrmOrganization() {
		return this.frmOrganization;
	}

	public void setFrmOrganization(OrganizationPO frmOrganization) {
		this.frmOrganization = frmOrganization;
	}

	@Column(name = "name", length = 200)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "code", length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "iconCls", length = 100)
	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Column(name = "seq", precision = 8, scale = 0)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frmOrganization", cascade = CascadeType.ALL)
	public Set<OrganizationPO> getFrmOrganizations() {
		return this.frmOrganizations;
	}

	public void setFrmOrganizations(Set<OrganizationPO> frmOrganizations) {
		this.frmOrganizations = frmOrganizations;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_userOrganization", schema = "", joinColumns = { @JoinColumn(name = "organization_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	public Set<UserPO> getFrmUsers() {
		return this.frmUsers;
	}

	public void setFrmUsers(Set<UserPO> frmUsers) {
		this.frmUsers = frmUsers;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_organizationResource", schema = "", joinColumns = { @JoinColumn(name = "organization_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) })
	public Set<ResourcePO> getFrmResources() {
		return this.frmResources;
	}

	public void setFrmResources(Set<ResourcePO> frmResources) {
		this.frmResources = frmResources;
	}

	/**
	 * 用于业务逻辑的字段，注解@Transient代表不需要持久化到数据库中
	 * 
	 * @return
	 */
	@Transient
	public String getPid() {
		if (frmOrganization != null && !StringUtils.isBlank(frmOrganization.getId())) {
			return frmOrganization.getId();
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "orgCode", length = 6)
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}
