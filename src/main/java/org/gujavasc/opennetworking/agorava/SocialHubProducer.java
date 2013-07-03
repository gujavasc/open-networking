package org.gujavasc.opennetworking.agorava;

import javax.enterprise.inject.Produces;

import org.agorava.LinkedIn;
import org.agorava.LinkedInServicesHub;
import org.agorava.core.api.oauth.Param;
import org.agorava.core.cdi.OAuthApplication;
import org.agorava.core.oauth.SimpleOAuthAppSettingsBuilder;

/**
 * Configures Agorava with the needed producers
 * 
 * @author <a href="mailto:gegastaldi@gmail.com">George Gastaldi</a>
 */
public class SocialHubProducer {

	@LinkedIn
	@OAuthApplication(builder = SimpleOAuthAppSettingsBuilder.class, params = { @Param(name = SimpleOAuthAppSettingsBuilder.API_KEY, value = "bnv8x51yjbdo"),
			@Param(name = SimpleOAuthAppSettingsBuilder.API_SECRET, value = "y0nvGzOYjw1KyKdV"),
			@Param(name = SimpleOAuthAppSettingsBuilder.CALLBACK, value = "/faces/callback.xhtml") })
	@Produces
	public LinkedInServicesHub produceLinkedInServiceHub(LinkedInServicesHub service) {
		return service;
	}
}
