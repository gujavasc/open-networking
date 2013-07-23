package org.gujavasc.opennetworking.domain.repository;

import javax.ejb.Stateless;

import org.gujavasc.opennetworking.domain.model.event.aggregator.Event;

@Stateless
public class EventRepository extends Repository<Event> {
	public EventRepository() {
		super(Event.class);
	}
}
