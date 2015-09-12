package svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;

import org.apache.commons.dbcp.BasicDataSource;
import svc.logging.LogSystem;

@SpringBootApplication
public class Application {

	@Bean
    public BasicDataSource dataSource()
	{
		String url = System.getenv("DATABASE_URL");
	
		if (url == null)
		{
			LogSystem.LogEvent("No system database URL set. Defaulting to Development Heroku URL.");
			url = "postgres://jcxzcbknwtgjwy:G1QxBLE7RvEqyBKW-1CaSeXlBQ@ec2-50-19-208-138.compute-1.amazonaws.com:5432/d8t45kme7cf49c";
		}
		
        URI dbUri = null;
        try
        {
        	dbUri = new URI(url);
        }
        catch (Exception e)
        {
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
	
    public static void main(String[] args) {
    	String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        System.setProperty("server.port", webPort);
        SpringApplication.run(Application.class, args);
    }
}
