package org.gujavasc.opennetworking.domain.model.user.aggregate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.gson.annotations.Expose;

@Embeddable
public class UserName {
	
	@Column
	@Expose
	private String firstName = "";

	@Column
	@Expose
	private String lastName = "";
	
	public UserName() {
	}
	
	public UserName(String firstName, String lastName) {
		if (firstName != null) {
			this.firstName = firstName;
		}
		
		if (lastName != null) {
			this.lastName = lastName;
		}
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
}
