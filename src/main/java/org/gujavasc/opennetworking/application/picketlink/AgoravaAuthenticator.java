/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gujavasc.opennetworking.application.picketlink;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthService;
import org.agorava.core.api.oauth.OAuthSession;
import org.agorava.linkedin.ProfileService;
import org.agorava.linkedin.model.LinkedInProfileFull;
import org.gujavasc.opennetworking.domain.repository.UserRepository;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.authentication.event.PreLoggedOutEvent;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.Credentials.Status;
import org.picketlink.idm.model.Attribute;
import org.picketlink.idm.model.SimpleUser;
import org.picketlink.idm.model.User;

@PicketLink
public class AgoravaAuthenticator extends BaseAuthenticator {

	@Inject
	DefaultLoginCredentials credentials;

	@Inject
	@PicketLink
	Instance<HttpServletResponse> response;

	@Inject
	private Logger logger;

	@Inject
	@LinkedIn
	OAuthService service;

	@EJB
	UserRepository userRepository;

	@Inject
	private ProfileService profileService;

	@Override
	public void authenticate() {
		if (service.isConnected()) {
			OAuthSession session = service.getSession();
			credentials.setCredential(session.getAccessToken());
			setStatus(AuthenticationStatus.SUCCESS);
			String id = session.getUserProfile().getId();
			User user = userRepository.findById(id);
			if (user == null) {
				logger.info(String.format("User '%s' does not exist in DB. Persisting...", id));
				LinkedInProfileFull userProfileFull = profileService.getUserProfileFull();
				user = toUser(userProfileFull);
				userRepository.persist(user);
			}
			logger.info(String.format("User '%s' logged in.", id));
			setAgent(user);
		} else {
			String authorizationUrl = service.getAuthorizationUrl();
			try {
				response.get().sendRedirect(authorizationUrl);
			} catch (IOException e) {
				// Ignore
			}
			credentials.setStatus(Status.IN_PROGRESS);
			setStatus(AuthenticationStatus.DEFERRED);
		}
	}

	// TODO: Move to another class ?
	private User toUser(LinkedInProfileFull profile) {
		SimpleUser simpleUser = new SimpleUser(profile.getId());
		simpleUser.setFirstName(profile.getFirstName());
		simpleUser.setLastName(profile.getLastName());
		simpleUser.setAttribute(new Attribute<String>("image", profile.getProfileImageUrl()));
		simpleUser.setAttribute(new Attribute<String>("skills", profile.getSkills().toString()));
		return simpleUser;
	}

	void logout(@Observes PreLoggedOutEvent preLoggedOutEvent) {
		service.getSession().setAccessToken(null);
	}
}
