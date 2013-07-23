package org.gujavasc.opennetworking.infra.converter;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializer implements JsonSerializer<Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateFormat.format(date));
	}

}