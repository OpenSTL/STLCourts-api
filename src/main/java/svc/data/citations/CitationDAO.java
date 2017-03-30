package svc.data.citations;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import rx.Observable;
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.models.Citation;
import svc.models.CitationDataSourceWrapper;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.List;

//NOTE: If we switch to groovy, we can greatly reduce code here since we can pass the function as an argument to a method
@Component
public class CitationDAO{
	@Inject
    private CitationDataSourceFactory citationDataSourceFactory;
	
	private List<CitationDataSourceWrapper> createDataSourceWrappers(List<Citation> citations, CITATION_DATASOURCE citationDataSource){
		List<CitationDataSourceWrapper> citationDataSourceWrappers = Lists.newArrayList(); 
		for(Citation citation : citations){
			CitationDataSourceWrapper citationDataSourceWrapper = new CitationDataSourceWrapper();
			citationDataSourceWrapper.citation = citation;
			citationDataSourceWrapper.citationDataSource = citationDataSource;
			citationDataSourceWrappers.add(citationDataSourceWrapper);
		}
		return citationDataSourceWrappers;
	}

	public List<CitationDataSourceWrapper> getByCitationNumberAndDOB(String citationNumber, LocalDate dob, List<CitationDataSource> sources) {
	    List<Observable<CitationDataSourceWrapper>> citationSearches = Lists.newArrayList();
		for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(createDataSourceWrappers(source.getByCitationNumberAndDOB(citationNumber, dob),source.getCitationDataSource())));
		}

		return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}

	public List<CitationDataSourceWrapper> getByCitationNumberAndDOB(String citationNumber, LocalDate dob) {
	    return getByCitationNumberAndDOB(citationNumber, dob, citationDataSourceFactory.getAllCitationDataSources());
	}

	public List<CitationDataSourceWrapper> getByLicenseAndDOB(String driversLicenseNumber, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getAllCitationDataSources();

        List<Observable<CitationDataSourceWrapper>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(createDataSourceWrappers(source.getByLicenseAndDOB(driversLicenseNumber, dob),source.getCitationDataSource())));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
	public List<CitationDataSourceWrapper> getByNameAndMunicipalitiesAndDOB(String lastName, List<Long> municipalities, LocalDate dob) {
        List<CitationDataSource> sources = citationDataSourceFactory.getCitationDataSourcesForMunicipalities(municipalities);

        List<Observable<CitationDataSourceWrapper>> citationSearches = Lists.newArrayList();
        for(CitationDataSource source : sources) {
            citationSearches.add(Observable.from(createDataSourceWrappers(source.getByNameAndMunicipalitiesAndDOB(lastName, municipalities, dob),source.getCitationDataSource())));
        }

        return Observable.merge(citationSearches).onExceptionResumeNext(Observable.just(null)).toList().toBlocking().first();
	}
	
}
