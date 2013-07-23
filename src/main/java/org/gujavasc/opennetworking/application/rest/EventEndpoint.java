package org.gujavasc.opennetworking.application.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.gujavasc.opennetworking.domain.aggregator.Event;
import org.jboss.logging.Logger;

@Stateless
@Path("/events")
public class EventEndpoint {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	private Logger logger;

	@POST
	@Consumes("application/json")
	public Response create(Event entity) {
		logger.info("Called 'create' method /resources/events/ [POST]");
		
		em.persist(entity);
		
		return Response.created(UriBuilder.fromResource(EventEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		logger.info("Called 'deleteById' method /resources/events/id [DELETE]");

		Event entity = em.find(Event.class, id);
	
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		em.remove(entity);
		
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		logger.info("Called 'findById' method /resources/events/id [GET]");
		
		Event entity = em.find(Event.class, id);
		
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Event> listAll() {
		logger.info("Called 'listAll' method /resources/events/ [GET]");

		return em.createQuery("FROM Event", Event.class).getResultList();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Event entity) {
		logger.info("Called 'deleteById' method /resources/events/id [DELETE]");
		
		entity.setId(id);
		
		entity = em.merge(entity);
		
		return Response.noContent().build();
	}
}