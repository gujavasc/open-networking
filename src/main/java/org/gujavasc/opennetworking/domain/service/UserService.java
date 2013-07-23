package org.gujavasc.opennetworking.domain.service;

import org.gujavasc.opennetworking.domain.model.user.aggregator.User;

public interface UserService {

	User getByIdSocialProfile(String idUserSocialProfile);
	
}
