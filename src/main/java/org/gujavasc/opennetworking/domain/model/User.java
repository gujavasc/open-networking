package org.gujavasc.opennetworking.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@Entity
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 2674929625431124423L;

	@Id
	@Expose
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long id = null;

	@Version
	@Column
	private int version = 0;

	@Column
	@Expose
	private String idSocialProfile;

	@Column
	@Expose
	private String firstName = "";

	@Column
	@Expose
	private String lastName = "";

	public User() {
	}

	public User(String idSocialProfile, String firstName, String lastName) {
		this.idSocialProfile = idSocialProfile;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getIdSocialProfile() {
		return idSocialProfile;
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

}
