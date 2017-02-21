package svc.hashids;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HashidsConfig {
	@Value("${stlcourts.security.municipalitySalt}")
	String municipalitySalt;
	@Value("${stlcourts.security.judgeSalt}")
	String judgeSalt;
	@Value("${stlcourts.security.courtSalt}")
	String courtSalt;
	@Bean
	public Hashids municipalityHashids() {
		//set hash length to 8
		return new Hashids(municipalitySalt,8);
	}
	@Bean
	public Hashids judgeHashids() {
		//set hash length to 8
		return new Hashids(judgeSalt,8);
	}
	@Bean
	public Hashids courtHashids() {
		//set hash length to 8
		return new Hashids(courtSalt,8);
	}
}
