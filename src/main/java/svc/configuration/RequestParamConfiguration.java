package svc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import svc.converter.LocalDateConverter;
import svc.converter.LocalDateTimeConverter;

@Configuration
@EnableWebMvc
public class RequestParamConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addFormatters(FormatterRegistry registry){
		registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
		registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
	}
}
