package svc.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
@EnableWebMvc
public class XmlConfiguration extends WebMvcConfigurerAdapter {
	
	 @Override
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createXmlHttpMessageConverter());
        super.configureMessageConverters(converters);
	 }

	 private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
		 Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
	     builder.indentOutput(true);
	     
	     return new MappingJackson2XmlHttpMessageConverter(builder.build());
	}
}
