package org.gujavasc.opennetworking.application.managedbean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.gujavasc.opennetworking.application.picketlink.LinkedInAgent;
import org.gujavasc.opennetworking.domain.model.user.aggregator.User;
import org.gujavasc.opennetworking.domain.repository.UserRepository;
import org.picketlink.Identity;
import org.picketlink.authentication.event.LoggedInEvent;

@Named
@SessionScoped
public class SocialUserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private transient UserRepository userRepository;

	@Inject
	private Identity identity;

	private User loggedUser;

	public void loadUser(@Observes LoggedInEvent loggedIn) {
		String idUserSocialProfile = identity.getAgent().getId();

		loggedUser = userRepository.getByIdSocialProfile(idUserSocialProfile);

		if (loggedUser == null) {
			persistNewUser();
		}
	}

	private void persistNewUser() {
		LinkedInAgent agent = (LinkedInAgent) identity.getAgent();
		User newUser = new User(agent.getId(), agent.getFirstName(), agent.getLastName());
		userRepository.persist(newUser);
		loggedUser = newUser;
	}
}
