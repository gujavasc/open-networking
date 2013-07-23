/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gujavasc.opennetworking.application.picketlink;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthService;
import org.agorava.core.api.oauth.OAuthSession;
import org.agorava.linkedin.ProfileService;
import org.agorava.linkedin.model.LinkedInProfileFull;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.authentication.event.PreLoggedOutEvent;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.credential.Credentials.Status;

@ApplicationScoped
@PicketLink
public class AgoravaAuthenticator extends BaseAuthenticator {

	@Inject
	DefaultLoginCredentials credentials;

	@Inject
	@PicketLink
	Instance<HttpServletRequest> request;

	@Inject
	@PicketLink
	Instance<HttpServletResponse> response;

	@Inject
	@LinkedIn
	OAuthService service;

	@Inject
	private transient ProfileService profileService;

	@Override
	public void authenticate() {
		if (service.isConnected()) {
			OAuthSession session = service.getSession();
			credentials.setCredential(session.getAccessToken());
			setStatus(AuthenticationStatus.SUCCESS);

			LinkedInProfileFull userProfileFull = profileService.getUserProfileFull();
			LinkedInAgent user = new LinkedInAgent(userProfileFull);
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

	public void logout(@Observes PreLoggedOutEvent preLoggedOutEvent) {
		service.getSession().setAccessToken(null);
	}
}
