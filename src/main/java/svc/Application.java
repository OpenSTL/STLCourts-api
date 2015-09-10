package svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;

import org.apache.commons.dbcp.BasicDataSource;

@SpringBootApplication
public class Application {

	@Bean
    public BasicDataSource dataSource() {
		String url = System.getenv("DATABASE_URL");
		url = "postgres://globalhack:12345@localhost:5432/globalhack";
		
        URI dbUri = null;
        try {
        	dbUri = new URI(url);
        }
        catch (Exception e)
        {
        	System.out.println("DataSource");
  	        System.out.println("Exception");
  	        System.out.println(e.getMessage());
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        System.out.println("DataSource Configuration:");
        System.out.println(basicDataSource.getUrl());
        
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
