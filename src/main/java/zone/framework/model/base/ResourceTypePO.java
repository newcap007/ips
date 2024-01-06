package zone.framework.model.base;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "frm_resourceType", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class ResourceTypePO extends Base implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "frmResourceType")
	public Set<ResourcePO> getFrmResources() {
		return this.frmResources;
	}

	public void setFrmResources(Set<ResourcePO> frmResources) {
		this.frmResources = frmResources;
	}

}
