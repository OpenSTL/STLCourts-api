package svc.gson.adapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeJsonAdapter extends TypeAdapter<LocalDateTime>{

	@Override
	public void write(JsonWriter out, LocalDateTime value) throws IOException {
		out.value(value.toString());
		
	}

	@Override
	public LocalDateTime read(JsonReader in) throws IOException {
		return LocalDateTime.parse(in.nextString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}

