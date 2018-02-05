package svc.data.citations.datasources.importedITI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImportedItiConfiguration {

	@Value("${stlcourts.citationDataSources.importedITI.rootUrl}")
	public String rootUrl;
	
	@Value("${stlcourts.citationDataSources.importedITI.apiKey}")
	public String apiKey;
}
