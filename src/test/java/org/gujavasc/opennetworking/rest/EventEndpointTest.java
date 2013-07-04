package org.gujavasc.opennetworking.rest;


import static org.junit.Assert.*;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.gujavasc.opennetworking.model.Event;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.idm.jpa.schema.IdentityObject;

@RunWith(Arquillian.class)
public class EventEndpointTest
{
   @Inject
   private EventEndpoint eventendpoint;
   
   @Deployment
   public static JavaArchive createDeployment()
   {
      return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addClass(EventEndpoint.class)
            .addPackage(Event.class.getPackage())
            .addPackage(IdentityObject.class.getPackage())
            .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
   }
   
   @Test
   public void testIsDeployed()
   {
      assertNotNull(eventendpoint);
   }
   
   @Test
   public void create(){
	   Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
	   eventendpoint.create(event);
	   assertNotNull(event.getId());
//	   eventendpoint.deleteById(event.getId());
   }
   
   @Test
   public void read(){
	   Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
	   eventendpoint.create(event);
	   Response response = eventendpoint.findById(event.getId());
	   event = (Event) response.getEntity();
	   assertNotNull(event);
	   eventendpoint.deleteById(event.getId());
   }
   
   @Test
   public void update(){
	   Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
	   eventendpoint.create(event);
	   String newName = "GujavaSC-Update";
	   event.setName(newName);
	   eventendpoint.update(event.getId(), event);
	   Response response = eventendpoint.findById(event.getId());
	   event = (Event) response.getEntity();
	   assertEquals(newName, event.getName());
	   eventendpoint.deleteById(event.getId());
   }
   
   
   @Test
   public void delete(){
	   Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
	   eventendpoint.create(event);
	   eventendpoint.deleteById(event.getId());
	   Response response = eventendpoint.findById(event.getId());
	   assertNull(response.getEntity());
   }
}
