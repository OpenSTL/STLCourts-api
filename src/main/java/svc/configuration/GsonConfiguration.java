package svc.configuration;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.http.converter.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import svc.security.HashUtil;
import svc.types.HashableEntity;

@Configuration
@EnableWebMvc
public class GsonConfiguration extends WebMvcConfigurerAdapter {
	 @Inject
 	 HashUtil hashUtil;
	
	 @Override
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createGsonHttpMessageConverter());
        super.configureMessageConverters(converters);
	 }

	 private GsonHttpMessageConverter createGsonHttpMessageConverter() {
		Gson gsonWithConverter = new GsonBuilder()
									.registerTypeHierarchyAdapter(HashableEntity.class, new HashIdJsonAdapter())
									.registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
									.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
									.create();

        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gsonWithConverter);

        return gsonConverter;
	 }
	 
	@SuppressWarnings("rawtypes")
	private class HashIdJsonAdapter extends TypeAdapter<HashableEntity>{

		@Override
		public void write(JsonWriter out, HashableEntity hashableEntity) throws IOException {
			out.value(hashUtil.encode(hashableEntity.getType(), hashableEntity.getValue()));
			
		}

		@Override
		public HashableEntity read(JsonReader in) throws IOException {
			//currently there is no way to identify what type of HashableEntity to create
			//if needed to be implemented, a different type of TypeAdapter might be needed:
			//see: http://stackoverflow.com/questions/29860545/how-do-i-use-custom-deserialization-on-gson-for-generic-type
			return null;
		} 
	 }
	
	private class LocalDateJsonAdapter extends TypeAdapter<LocalDate>{

		@Override
		public void write(JsonWriter out, LocalDate value) throws IOException {
			if (value != null){
				out.value(value.toString());
			}else{
				out.value("");
			}
			
		}

		@Override
		public LocalDate read(JsonReader in) throws IOException {
			String dateString = in.nextString();
			if (dateString != "" && dateString != null){
				return LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}else{
				return null;
			}
		}
	}
	
	private class LocalDateTimeJsonAdapter extends TypeAdapter<LocalDateTime>{

		@Override
		public void write(JsonWriter out, LocalDateTime value) throws IOException {
			if (value !=null){
				out.value(value.toString());
			}else{
				out.value("");
			}
			
		}

		@Override
		public LocalDateTime read(JsonReader in) throws IOException {
			String dateTimeString = in.nextString();
			if (dateTimeString != "" && dateTimeString != null)
				return LocalDateTime.parse(dateTimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			else
				return null;
		}

	}
	
}
