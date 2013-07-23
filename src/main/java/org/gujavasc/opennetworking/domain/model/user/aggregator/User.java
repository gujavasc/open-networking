package org.gujavasc.opennetworking.domain.model.user.aggregator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.gujavasc.opennetworking.domain.model.user.aggregate.UserName;

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
	
	@Embedded
	private UserName name;
	
	public User() {
	}
	
	public User(String idSocialProfile, String firstName, String lastName) {
		this.idSocialProfile = idSocialProfile;
		this.name = new UserName(firstName, lastName);
	}
	
	public String getIdSocialProfile() {
		return idSocialProfile;
	}
	
	public String getFullName() {
		return this.name.getFullName();
	}

}
