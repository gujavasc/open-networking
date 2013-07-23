package org.gujavasc.opennetworking.infra.factory;

import java.util.Date;

import org.gujavasc.opennetworking.domain.aggregator.Event;
import org.gujavasc.opennetworking.infra.converter.DateDeserializer;
import org.gujavasc.opennetworking.infra.converter.DateSerializer;
import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {
	
	public static void main(String[] args) {
		Event event = new Event("TDC2013", "Florianópolis/SC", DateTime.now().toDate(), DateTime.now().plusDays(2).toDate());

		Gson gson = GsonFactory.createGson();
		
		String json = gson.toJson(event);
		
		System.out.println(json);
		
		Event event_ = gson.fromJson(json, Event.class);
		
		System.out.println(event_);
	}
	
	public static Gson createGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		return gsonBuilder.create();
	}

}
