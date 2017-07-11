package svc.data.citations.datasources.rejis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RejisConfiguration {

	@Value("${stlcourts.citationDataSources.rejis.rootUrl}")
	public String rootUrl;

	@Value("${stlcourts.citationDataSources.rejis.apiKey}")
	public String apiKey;
}
