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
@Table(name = "frm_resource", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ResourcePO extends Base implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String pid;// 虚拟属性，用于获得当前资源的父资源ID

	private String name;
	private String url;
	private String description;
	private String iconCls;
	private Integer seq;
	private String target;
	private ResourceTypePO frmResourceType;
	private ResourcePO frmResource;
	private Set<RolePO> frmRoles = new HashSet<RolePO>(0);
	private Set<OrganizationPO> frmOrganizations = new HashSet<OrganizationPO>(0);
	private Set<ResourcePO> frmResources = new HashSet<ResourcePO>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resourceType_id")
	public ResourceTypePO getFrmResourceType() {
		return this.frmResourceType;
	}

	public void setFrmResourceType(ResourceTypePO frmResourceType) {
		this.frmResourceType = frmResourceType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id")
	public ResourcePO getFrmResource() {
		return this.frmResource;
	}

	public void setFrmResource(ResourcePO frmResource) {
		this.frmResource = frmResource;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 200)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "target", length = 100)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_roleResource", schema = "", joinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<RolePO> getFrmRoles() {
		return this.frmRoles;
	}

	public void setFrmRoles(Set<RolePO> frmRoles) {
		this.frmRoles = frmRoles;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_organizationResource", schema = "", joinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "organization_id", nullable = false, updatable = false) })
	public Set<OrganizationPO> getFrmOrganizations() {
		return this.frmOrganizations;
	}

	public void setFrmOrganizations(Set<OrganizationPO> frmOrganizations) {
		this.frmOrganizations = frmOrganizations;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frmResource", cascade = CascadeType.ALL)
	public Set<ResourcePO> getFrmResources() {
		return this.frmResources;
	}

	public void setFrmResources(Set<ResourcePO> frmResources) {
		this.frmResources = frmResources;
	}

	@Transient
	public String getPid() {
		if (frmResource != null && !StringUtils.isBlank(frmResource.getId())) {
			return frmResource.getId();
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
