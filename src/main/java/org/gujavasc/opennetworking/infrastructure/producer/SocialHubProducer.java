package org.gujavasc.opennetworking.infrastructure.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;

import org.agorava.LinkedIn;
import org.agorava.core.api.oauth.OAuthAppSettings;
import org.agorava.core.api.oauth.OAuthAppSettingsBuilder;
import org.agorava.core.api.oauth.OAuthSession;
import org.agorava.core.cdi.Current;
import org.agorava.core.oauth.SimpleOAuthAppSettingsBuilder;

/**
 * Configures Agorava with the needed producers
 * 
 * @author <a href="mailto:gegastaldi@gmail.com">George Gastaldi</a>
 */
public class SocialHubProducer {

	@LinkedIn
	@Produces
	@ApplicationScoped
	public OAuthAppSettings produceLinkedInSettings() {
		OAuthAppSettingsBuilder builder = new SimpleOAuthAppSettingsBuilder();
		return builder.apiKey("bnv8x51yjbdo").apiSecret("y0nvGzOYjw1KyKdV").callback("oauth_callback").build();
	}

	@Produces
	@LinkedIn
	@Current
	@SessionScoped
	public OAuthSession produceOauthSession(@LinkedIn @Default OAuthSession session) {
		return session;
	}

}
