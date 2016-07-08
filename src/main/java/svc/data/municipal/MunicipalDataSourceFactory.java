package svc.data.municipal;

import java.net.URI;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import svc.logging.LogSystem;

@Component
public class MunicipalDataSourceFactory {

	@Value("${database.url:#{null}}")
	private String databaseURL;

	@Value("${database.type:POSTGRES}")
	private String databaseType;

	@Value("${mysql.username:#{null}}")
	private String mysqlUsername;

	@Value("${mysql.password:#{null}}")
	private String mysqlPassword;

	@Bean
	public BasicDataSource dataSource() {
		LogSystem.LogEvent("Creating new municipal data source...");
		
		if (databaseType.equalsIgnoreCase("MYSQL")) {
			return BuildMysqlDataSource();
		} else {
			return BuildHerokuDataSource();
		}
	}

	public BasicDataSource BuildMysqlDataSource() {
		if (databaseURL == null || mysqlUsername == null || mysqlPassword == null) {
			throw new NullPointerException();
		}

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mysql://" + databaseURL);
		basicDataSource.setUsername(mysqlUsername);
		basicDataSource.setPassword(mysqlPassword);

		LogSystem.LogEvent("DataSource Configuration:");
		LogSystem.LogEvent("  " + basicDataSource.getUrl());

		return basicDataSource;
	}

	public BasicDataSource BuildHerokuDataSource() {
		if (databaseURL == null) {
			LogSystem.LogEvent("No system database URL set. Defaulting to Development Heroku URL.");
			databaseURL = "postgres://jcxzcbknwtgjwy:G1QxBLE7RvEqyBKW-1CaSeXlBQ@ec2-50-19-208-138.compute-1.amazonaws.com:5432/d8t45kme7cf49c";
		}

		URI dbUri = null;
		try {
			dbUri = new URI(databaseURL);
		} catch (Exception e) {
			LogSystem.LogEvent("DataSource Exception - " + e.getMessage());
		}

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		basicDataSource.addConnectionProperty("ssl", "true");
		basicDataSource.addConnectionProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");

		LogSystem.LogEvent("DataSource Configuration:");
		LogSystem.LogEvent("  " + basicDataSource.getUrl());

		return basicDataSource;
	}
}
