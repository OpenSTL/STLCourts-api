package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import rx.Observable;
import svc.models.Citation;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

//NOTE: If we switch to groovy, we can greatly reduce code here since we can pass the function as an argument to a method
@Component
public class CitationDAO {
	@Inject
    private CitationDataSourceFactory citationDataSourceFactory;

	public List<Citation> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
	    List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
		for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByCitationNumberAndDOB(citationNumber, dob)).onExceptionResumeNext(Observable.just(null)));
		}

		return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}

	public List<Citation> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByLicenseAndDOB(driversLicenseNumber, dob)));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	public List<Citation> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getCitationDataSourcesForMunicipalities(municipalities);

        List<Observable<Citation>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(source.getByNameAndMunicipalitiesAndDOB(lastName, municipalities, dob)));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
}
