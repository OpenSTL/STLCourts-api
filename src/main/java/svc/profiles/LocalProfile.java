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
				.addScript("hsql/judges.sql")
				.addScript("hsql/opportunities.sql")
				.addScript("hsql/opportunity-needs.sql")
				.addScript("hsql/sponsor-login.sql")
				.addScript("hsql/sponsors.sql")
				.addScript("hsql/violations.sql")
				.addScript("hsql/citations.sql")
				.build();
	}
}
