package org.gujavasc.opennetworking.view;

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

@Named
@SessionScoped
public class SocialUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@LinkedIn
	OAuthService service;

	@Inject
	private transient ProfileService profileService;

	private LinkedInProfileFull profileFull;

	public void observeLoginOutcome(@Observes OAuthComplete complete) {
		if (complete.getStatus() == Status.SUCCESS) {
			// this.profileFull = complete.getEventData().getUserProfile();
			this.profileFull = profileService.getUserProfileFull();
		}
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
