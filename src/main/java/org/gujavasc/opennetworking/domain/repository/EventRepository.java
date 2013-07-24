package org.gujavasc.opennetworking.domain.repository;

import javax.ejb.Stateless;

import org.gujavasc.opennetworking.domain.model.Event;

@Stateless
public class EventRepository extends AbstractJPARepository<Event> {
	public EventRepository() {
		super(Event.class);
	}

}
