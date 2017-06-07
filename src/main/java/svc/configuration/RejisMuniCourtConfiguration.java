package svc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import svc.data.citations.datasources.rejis.RejisCitationDataSource;

@Configuration
public class RejisMuniCourtConfiguration {
	@Bean
	public Jaxb2Marshaller marshaller(){
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("svc.data.citations.datasources.regis.org.tempuri");
		return marshaller;
	}
	
	@Bean
	public RejisCitationDataSource rejisCitationDataSource(Jaxb2Marshaller marshaller) {
		RejisCitationDataSource client = new RejisCitationDataSource();
		client.setDefaultUri("http://rejspaapp1/municourtservices/MuniCourt.svc");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
