package rf.product.model;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String createdBy;
	private Date creationTime;
	private String modifiedBy;
	private Date modificationTime;
	private String organization;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}


	@PrePersist
	protected void prePersist() {
		this.creationTime = new Date();
		this.modificationTime = new Date();

//		AuthenticationFacade authFacade = ApplicationContextUtils.getBean(AuthenticationFacade.class);
//		if(authFacade!=null) {
//			if(authFacade.getAuthentication()!=null) {
//				this.createdBy = authFacade.getAuthentication().getName();
//				this.modifiedBy = authFacade.getAuthentication().getName();
//			} else {
//				this.createdBy = "_UNKNOWN";
//				this.modifiedBy = "_UNKNOWN";
//			}
//			if(this.company == null)
//				this.company = authFacade.getCompany();
//			if(this.country == null)
//				this.country = authFacade.getCountry();
//		}
	}

	@PreUpdate
	protected void preUpdate() {
//		Preconditions.checkNotNull(country, "[FastFail]Country cannot be set null");
//		Preconditions.checkNotNull(company, "[FastFail]Company cannot be set null");
		this.modificationTime = new Date();

//		AuthenticationFacade authFacade = ApplicationContextUtils.getBean(AuthenticationFacade.class);
//		if(authFacade!=null && authFacade.getAuthentication()!=null) {
//			this.modifiedBy = authFacade.getAuthentication().getName();
//		} else {
//			this.modifiedBy = "_UNKNOWN";
//		}
	}
}
