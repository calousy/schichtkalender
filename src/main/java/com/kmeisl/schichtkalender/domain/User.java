package com.kmeisl.schichtkalender.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This entity class is reverse engineered using NetBeans from Azure's example
 * dataset for SQL Server.
 *
 * @author Matti Tahvonen
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT c FROM User c"),
    @NamedQuery(name = "User.findByUserID", query = "SELECT c FROM User c WHERE c.userID = :userID"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT c FROM User c WHERE c.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT c FROM User c WHERE c.lastName = :lastName"),
    @NamedQuery(name = "User.findByEmailAddress", query = "SELECT c FROM User c WHERE c.emailAddress = :emailAddress"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT c FROM User c WHERE c.phone = :phone"),
    @NamedQuery(name = "User.findByPasswordHash", query = "SELECT c FROM User c WHERE c.passwordHash = :passwordHash"),
    @NamedQuery(name = "User.findByPasswordSalt", query = "SELECT c FROM User c WHERE c.passwordSalt = :passwordSalt"),
    @NamedQuery(name = "User.findByRowguid", query = "SELECT c FROM User c WHERE c.rowguid = :rowguid"),
    @NamedQuery(name = "User.findByModifiedDate", query = "SELECT c FROM User c WHERE c.modifiedDate = :modifiedDate")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "UserID")
    private Integer userID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FirstName")
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LastName")
    private String lastName;
    @Size(max = 50)
    @Column(name = "EmailAddress")
    private String emailAddress;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 25)
    @Column(name = "Phone")
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "PasswordHash")
    private String passwordHash;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PasswordSalt")
    private String passwordSalt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "rowguid")
    private String rowguid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "IsAdmin")
    private Boolean isAdmin;

    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String firstName, String lastName,
            String passwordHash, String passwordSalt, String rowguid,
            Date modifiedDate) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.rowguid = rowguid;
        this.modifiedDate = modifiedDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getRowguid() {
        return rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public Boolean isAdmin() {
		return isAdmin;
	}
    
    public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.
                equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kmeisl.model.User[ userID=" + userID + " ]";
    }

}