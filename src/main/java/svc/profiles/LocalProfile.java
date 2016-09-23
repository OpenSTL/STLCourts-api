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
//	@Bean
//	public DataSource dataSource() {
//		EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
//		return dbBuilder.setType(EmbeddedDatabaseType.HSQL).addScript("schema-tables.sql").addScript("data.sql").build();
//	}
}
