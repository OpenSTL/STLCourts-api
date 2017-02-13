package svc.data.citations.datasources.tyler;

import org.springframework.beans.factory.annotation.Autowired;
import svc.data.citations.CitationDataSource;

public class TylerCitationDataSource implements CitationDataSource {
    @Autowired
    private TylerConfiguration tylerConfiguration;
}
