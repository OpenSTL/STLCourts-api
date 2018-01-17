package svc.data.citations.datasources.imported;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImportedConfiguration {

	@Value("${stlcourts.citationDataSources.imported.rootUrl}")
	public String rootUrl;
}
