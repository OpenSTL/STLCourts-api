package svc.data.citations;

import org.springframework.stereotype.Component;
import svc.models.Citation;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
public class CitationDAO {
	@Inject
    private CitationDataSourceFactory citationDataSourceFactory;

	public Citation getByCitationNumberAndDOB(String citationNumber, Date dob) {
	    List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        //TODO
        return null;
	}
	
	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, Date dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        //TODO
        return null;
	}
	
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, Date dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getCitationDataSourcesForMunicipalities(municipalities);

        //TODO
        return null;
	}
}
