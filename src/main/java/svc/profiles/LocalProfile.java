package svc.profiles;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@Profile("local")
public class LocalProfile {
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
		return dbBuilder.setType(EmbeddedDatabaseType.HSQL)
				.addScript("sql/schema/schema-tables.sql")
				.addScript("hsql/courts.sql")
				.addScript("hsql/municipalities.sql")
				.addScript("hsql/municipality-court.sql")
				.addScript("hsql/judges.sql")
				.addScript("hsql/violations.sql")
				.addScript("hsql/citations.sql")
				.addScript("hsql/citation-datasource-municipality.sql")
				.addScript("hsql/citation-datasource.sql")
				.addScript("hsql/courtMappers/tyler-court-mapper.sql")
				.addScript("hsql/courtMappers/imported-court-mapper.sql")
				.addScript("hsql/courtMappers/rejis-court-mapper.sql")
				.addScript("hsql/datasource-municipality-mapping.sql")
				.build();
	}
	
}
