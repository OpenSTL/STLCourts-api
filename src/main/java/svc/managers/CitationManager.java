package svc.managers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import svc.data.citations.CitationDAO;
import svc.data.citations.CitationDataSourceFactory;
import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.CitationDataSourceWrapper;

import javax.inject.Inject;
import java.util.List;

@Component
public class CitationManager {
	@Inject
	private CitationDAO citationDAO;
	@Inject
	private ViolationManager violationManager;
	@Inject
	private CitationDataSourceFactory citationDataSourceFactory;
	
	public List<CitationDataSourceWrapper> findCitationsInDataSourceWrapper(CitationSearchCriteria criteria, String citationDataSource) {
		// Search by DOB & citation number
		if (criteria.dateOfBirth != null && criteria.citationNumber != null) {
			List<CitationDataSourceWrapper> citationDataSourceWrappers;
			if (citationDataSource != ""){
				citationDataSourceWrappers = citationDAO.getByCitationNumberAndDOB(criteria.citationNumber, criteria.dateOfBirth,citationDataSourceFactory.getDataSourceFromString(citationDataSource));
			}else{
				citationDataSourceWrappers = citationDAO.getByCitationNumberAndDOB(criteria.citationNumber, criteria.dateOfBirth);
			}
			return populateViolations(citationDataSourceWrappers);
		}
		
		// DOB & License No
		if (criteria.dateOfBirth != null && criteria.driversLicenseNumber != null) {
			List<CitationDataSourceWrapper> citationDataSourceWrappers = citationDAO.getByLicenseAndDOB(criteria.driversLicenseNumber, criteria.dateOfBirth);
			return populateViolations(citationDataSourceWrappers);
		}
		
		// DOB & Name & Municipality
		if (criteria.dateOfBirth != null && criteria.lastName != null && criteria.municipalities != null && criteria.municipalities.size() != 0) {
			List<CitationDataSourceWrapper> citationDataSourceWrappers =  citationDAO.getByNameAndMunicipalitiesAndDOB(criteria.lastName, criteria.municipalities, criteria.dateOfBirth);
			return populateViolations(citationDataSourceWrappers);
		}
		
		LogSystem.LogEvent("Not enough information was passed as criteria to find citations");
		return Lists.newArrayList();
	}
	
	public List<CitationDataSourceWrapper> findCitationsInDataSourceWrapper(CitationSearchCriteria criteria) {
		return findCitationsInDataSourceWrapper(criteria, "");
	}
	
	public List<Citation> findCitations(CitationSearchCriteria criteria) {
		return getCitationsFromWrapper(findCitationsInDataSourceWrapper(criteria));
	}
	
	public List<Citation> findCitations(CitationSearchCriteria criteria, String citationDataSource) {
		return getCitationsFromWrapper(findCitationsInDataSourceWrapper(criteria,citationDataSource));
	}
	
	public List<Citation> getCitationsFromWrapper(List<CitationDataSourceWrapper> citationDataSourceWrappers){
		List<Citation> citations = Lists.newArrayList();
		for (CitationDataSourceWrapper citationDataSourceWrapper:citationDataSourceWrappers) {
			citations.add(citationDataSourceWrapper.citation);
		}
		
		return citations;
	}
	
	private List<CitationDataSourceWrapper> populateViolations(List<CitationDataSourceWrapper> citationDataSourceWrappers) {
		if (citationDataSourceWrappers == null) {
			return null;
		}
		
		for (CitationDataSourceWrapper citationDataSourceWrapper:citationDataSourceWrappers) {
			citationDataSourceWrapper.citation.violations = violationManager.getViolationsByCitationNumber(citationDataSourceWrapper.citation.citation_number);
		}
		return citationDataSourceWrappers;
	}
}