package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import rx.Observable;
import svc.models.Citation;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
public class CitationDAO {
	@Inject
    private CitationDataSourceFactory citationDataSourceFactory;

	public List<Citation> getByCitationNumberAndDOB(String citationNumber, Date dob) {
	    List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
		for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByCitationNumberAndDOB(citationNumber, dob)));
		}

		return Observable.merge(citationSearches).toList().toBlocking().first();
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
