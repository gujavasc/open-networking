package org.gujavasc.opennetworking.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.agorava.LinkedIn;
import org.agorava.core.api.UserProfile;
import org.agorava.core.api.event.OAuthComplete;
import org.agorava.core.api.event.SocialEvent.Status;
import org.agorava.core.api.oauth.OAuthService;

@Named
@SessionScoped
public class SocialUserBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Inject
   @LinkedIn
   OAuthService service;

   private UserProfile profileFull;

   public void observeLoginOutcome(@Observes OAuthComplete complete)
   {
      if (complete.getStatus() == Status.SUCCESS)
      {
         this.profileFull = complete.getEventData().getUserProfile();
      }
   }

   public UserProfile getProfileFull()
   {
      return profileFull;
   }

   public OAuthService getService()
   {
      return service;
   }

   public void logout()
   {
      service.getSession().setAccessToken(null);
      profileFull = null;
   }

}
