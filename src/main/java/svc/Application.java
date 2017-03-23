package svc;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@ComponentScan
public class Application {
	
	public static void main(String[] args) {
		 new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }
}
