package org.gujavasc.opennetworking.domain.service;

import javax.inject.Inject;

import org.gujavasc.opennetworking.domain.repository.EventRepository;

public class EventServiceImpl implements EventService {
	
	@Inject
	private EventRepository eventRepository;

}
