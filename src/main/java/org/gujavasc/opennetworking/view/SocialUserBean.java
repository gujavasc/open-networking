package org.gujavasc.opennetworking.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.agorava.LinkedIn;
import org.agorava.core.api.SocialMediaApiHub;
import org.agorava.core.api.UserProfile;
import org.agorava.core.api.event.OAuthComplete;
import org.agorava.core.api.event.SocialEvent.Status;

@Named
@SessionScoped
public class SocialUserBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Inject
   @LinkedIn
   private SocialMediaApiHub serviceHub;

   private UserProfile profileFull;

   @Named
   @Produces
   @SessionScoped
   private SocialMediaApiHub getServiceHub()
   {
      return serviceHub;
   }

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

}
