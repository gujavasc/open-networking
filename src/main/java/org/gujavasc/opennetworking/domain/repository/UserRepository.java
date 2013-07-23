package org.gujavasc.opennetworking.domain.repository;

import javax.ejb.Stateless;

import org.gujavasc.opennetworking.domain.model.user.aggregator.User;

@Stateless
public class UserRepository extends Repository<User> {
	public UserRepository() {
		super(User.class);
	}

	public User getByIdSocialProfile(String idUserSocialProfile) {
		return null;
	}

}
