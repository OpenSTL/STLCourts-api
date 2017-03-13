package svc.gson.configuration;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

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
}
