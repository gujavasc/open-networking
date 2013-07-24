package org.gujavasc.opennetworking.domain.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.gujavasc.opennetworking.domain.model.Event;

@Stateless
public class EventRepository extends Repository<Event> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	public EventRepository() {
		super(Event.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
}
