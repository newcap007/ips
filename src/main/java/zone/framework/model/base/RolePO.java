package zone.framework.model.base;

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

@Entity
@Table(name = "frm_role", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class RolePO extends Base implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	private String iconCls;
	private Integer seq;
	private Set<UserPO> frmUsers = new HashSet<UserPO>(0);
	private Set<ResourcePO> frmResources = new HashSet<ResourcePO>(0);

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_userRole", schema = "", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	public Set<UserPO> getFrmUsers() {
		return this.frmUsers;
	}

	public void setFrmUsers(Set<UserPO> frmUsers) {
		this.frmUsers = frmUsers;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_roleResource", schema = "", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "resource_id", nullable = false, updatable = false) })
	public Set<ResourcePO> getFrmResources() {
		return this.frmResources;
	}

	public void setFrmResources(Set<ResourcePO> frmResources) {
		this.frmResources = frmResources;
	}

}
