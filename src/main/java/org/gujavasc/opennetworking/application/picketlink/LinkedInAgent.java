/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.gujavasc.opennetworking.application.picketlink;

import org.agorava.linkedin.model.LinkedInProfileFull;
import org.picketlink.idm.model.SimpleUser;

/**
 * A {@link LinkedInAgent} is authenticated in {@link AgoravaAuthenticator}
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 * 
 */
public class LinkedInAgent extends SimpleUser {
	private static final long serialVersionUID = 1L;

	private LinkedInProfileFull profile;

	public LinkedInAgent(LinkedInProfileFull profile) {
		super(profile.getId());
		this.profile = profile;
		setFirstName(profile.getFirstName());
		setLastName(profile.getLastName());
	}

	public LinkedInProfileFull getProfile() {
		return profile;
	}
}
