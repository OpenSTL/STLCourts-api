package svc.data.citations.datasources.tyler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "stlcourts.citationDataSources.tyler")
public class TylerConfiguration {
    public String rootUrl;
    public String apyKey;
}
