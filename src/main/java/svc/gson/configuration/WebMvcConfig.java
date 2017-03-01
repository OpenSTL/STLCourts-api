package svc.gson.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	Gson gsonWithConverter;
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.add(createGsonHttpMessageConverter());
       super.configureMessageConverters(converters);
	 }

	private GsonHttpMessageConverter createGsonHttpMessageConverter() {
		gsonWithConverter = new GsonBuilder().create();

        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gsonWithConverter);

        return gsonConverter;
	}

}
