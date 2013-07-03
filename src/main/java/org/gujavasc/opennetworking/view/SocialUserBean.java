package org.gujavasc.opennetworking.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.agorava.LinkedIn;
import org.agorava.core.api.SocialMediaApiHub;
import org.agorava.core.api.event.OAuthComplete;
import org.agorava.core.api.event.SocialEvent.Status;
import org.agorava.linkedin.ProfileService;
import org.agorava.linkedin.model.LinkedInProfile;

@Named
@SessionScoped
public class SocialUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@LinkedIn
	private SocialMediaApiHub serviceHub;

	private LinkedInProfile profileFull;

	private @Named
	@Produces
	@SessionScoped
	SocialMediaApiHub getServiceHub() {
		return serviceHub;
	}

	public void observeLoginOutcome(@Observes OAuthComplete complete, ProfileService profileService) {
		if (complete.getStatus() == Status.SUCCESS) {
			this.profileFull = profileService.getUserProfile();
		}
	}

	public LinkedInProfile getProfileFull() {
		return profileFull;
	}

}
