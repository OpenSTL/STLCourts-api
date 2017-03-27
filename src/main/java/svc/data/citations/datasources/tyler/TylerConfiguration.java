package svc.data.citations.datasources.tyler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TylerConfiguration {

	@Value("${stlcourts.citationDataSources.tyler.rootUrl}")
	public String rootUrl;

	@Value("${stlcourts.citationDataSources.tyler.apiKey}")
	public String apiKey;
}
