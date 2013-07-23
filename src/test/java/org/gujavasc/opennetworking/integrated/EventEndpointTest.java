package org.gujavasc.opennetworking.integrated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.gujavasc.opennetworking.application.rest.EventEndpoint;
import org.gujavasc.opennetworking.application.rest.JaxRsActivator;
import org.gujavasc.opennetworking.domain.aggregator.Event;
import org.gujavasc.opennetworking.infra.factory.GsonFactory;
import org.gujavasc.opennetworking.infrastructure.producer.LoggerProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketlink.idm.jpa.schema.IdentityObject;

@RunWith(Arquillian.class)
public class EventEndpointTest {
	
	private static final String EVENT_ENDPOINT_URL = "http://localhost:8080/open-networking/resources/events";
	
	@Inject
	private EventEndpoint eventendpoint;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class) //
						 .addClass(EventEndpoint.class) //
						 .addClass(JaxRsActivator.class) //
						 .addPackage(Event.class.getPackage()) //
					 	 .addPackage(LoggerProducer.class.getPackage()) //
					 	 .addPackage(IdentityObject.class.getPackage()) //
						 .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml") //
						 .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	@RunAsClient
	public void list_all_events_by_rest() throws Exception {
		ClientRequest request = new ClientRequest(EVENT_ENDPOINT_URL);
		request.accept("application/json");

		ClientResponse<String> response = request.get(String.class);

		if (response.getStatus() != 200) {
			Assert.fail("Code status response: " + response.getStatus());
		}

//		Type type = new TypeToken<ArrayList<Event>>(){}.getType();
//		List<Event> events = GsonFactory.createGson().fromJson(response.getEntity(), type);
		
//		Assert.assertNotNull(events);
		
//		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
//
//		String output;
//
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
	}

	@Test
	@RunAsClient
	public void create_new_event_by_rest() throws Exception {
		ClientRequest request = new ClientRequest(EVENT_ENDPOINT_URL);
		request.accept("application/json");

		Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
		
		request.body("application/json", GsonFactory.createGson().toJson(event));

		ClientResponse<String> response = request.post(String.class);

		if (response.getStatus() != 201) {
			Assert.fail("Failed - HTTP error code: " + response.getStatus());
		}
	}

	@Test
	public void testIsDeployed() {
		assertNotNull(eventendpoint);
	}

	@Test
	public void create() {
		Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
		eventendpoint.create(event);
		assertNotNull(event.getId());
		eventendpoint.deleteById(event.getId());
	}

	@Test
	public void read() {
		Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
		eventendpoint.create(event);
		Response response = eventendpoint.findById(event.getId());
		event = (Event) response.getEntity();
		assertNotNull(event);
		eventendpoint.deleteById(event.getId());
	}

	@Test
	public void update() {
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
	public void delete() {
		Event event = new Event("GuvavaSC", "Evento de Java em SC", new Date(), new Date());
		eventendpoint.create(event);
		eventendpoint.deleteById(event.getId());
		Response response = eventendpoint.findById(event.getId());
		assertNull(response.getEntity());
	}

}
