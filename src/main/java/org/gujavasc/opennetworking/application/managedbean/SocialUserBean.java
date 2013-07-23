package org.gujavasc.opennetworking.application.managedbean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.agorava.LinkedIn;
import org.agorava.core.api.event.OAuthComplete;
import org.agorava.core.api.event.SocialEvent.Status;
import org.agorava.core.api.oauth.OAuthService;
import org.agorava.linkedin.ProfileService;
import org.agorava.linkedin.model.LinkedInProfileFull;
import org.gujavasc.opennetworking.domain.model.user.aggregator.User;
import org.gujavasc.opennetworking.domain.repository.UserRepository;
import org.gujavasc.opennetworking.domain.service.UserService;

@Named
@SessionScoped
public class SocialUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@LinkedIn
	OAuthService service;

	@Inject
	private transient ProfileService profileService;
	
	@Inject
	private transient UserRepository userRepository;

	private LinkedInProfileFull profileFull;
	
	private User loggedUser;

	public void observeLoginOutcome(@Observes OAuthComplete complete) {
		if (complete.getStatus() == Status.SUCCESS) {
			profileFull = profileService.getUserProfileFull();
		}
	}

	public void loadUser(@Observes OAuthComplete complete) {
		if (complete.getStatus() == Status.SUCCESS) {
			String idUserSocialProfile = profileFull.getId();
			
			loggedUser = userRepository.getByIdSocialProfile(idUserSocialProfile);
			
			if (loggedUser == null) {
				persistNewUser();
			}
		}
	}

	private void persistNewUser() {
		User newUser = new User(profileFull.getId(), profileFull.getFirstName(), profileFull.getLastName());
		userRepository.persist(newUser);
		loggedUser = newUser;
	}

	public LinkedInProfileFull getProfileFull() {
		return profileFull;
	}

	public OAuthService getService() {
		return service;
	}

	public void logout() {
		service.getSession().setAccessToken(null);
		profileFull = null;
	}

}
