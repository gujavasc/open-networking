/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gujavasc.opennetworking.application.picketlink;

import java.io.IOException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthService;
import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;

/**
 * Invokes authentication again and redirects to home page
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 * 
 */
@WebServlet(name = "oauth_callback", urlPatterns = "/oauth_callback")
public class OAuthCallbackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	@LinkedIn
	Instance<OAuthService> services;

	@Inject
	Identity identity;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OAuthService service = services.get();
		String verifier = req.getParameter(service.getVerifierParamName());
		service.setVerifier(verifier);
		service.initAccessToken();
		AuthenticationResult result = identity.login();
		if (result == AuthenticationResult.FAILED) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			// Redirect to home page
			resp.sendRedirect(req.getContextPath());
		}

	}

}
