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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "frm_user", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UserPO extends Base implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip;// 此属性不存数据库，虚拟属性

	private String loginName;
	private String pwd;
	private String name;
	private String sex;
	private Integer age;
	private String photo;
	//业务组织编码
	private String orgCode;
	private OrganizationPO frmOrganization;
	private Set<OrganizationPO> frmOrganizations = new HashSet<OrganizationPO>(0);
	private Set<RolePO> frmRoles = new HashSet<RolePO>(0);

	@Column(name = "loginName", nullable = false, length = 20)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "pwd", length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "age", precision = 8, scale = 0)
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "photo", length = 200)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_userOrganization", schema = "", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "organization_id", nullable = false, updatable = false) })
	public Set<OrganizationPO> getFrmOrganizations() {
		return this.frmOrganizations;
	}

	public void setFrmOrganizations(Set<OrganizationPO> frmOrganizations) {
		this.frmOrganizations = frmOrganizations;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "frm_userRole", schema = "", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	public Set<RolePO> getFrmRoles() {
		return this.frmRoles;
	}

	public void setFrmRoles(Set<RolePO> frmRoles) {
		this.frmRoles = frmRoles;
	}

	@Transient
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "orgCode", length = 6)
	public String getOrgCode() {
		return orgCode;
	}
	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="organization_id")//注释本表中指向另一个表的外键。
	public OrganizationPO getFrmOrganization() {
		return frmOrganization;
	}

	public void setFrmOrganization(OrganizationPO frmOrganization) {
		this.frmOrganization = frmOrganization;
	}
	
}
