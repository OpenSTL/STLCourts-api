package svc.managers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import svc.data.citations.CitationDAO;
import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.models.Citation;

import javax.inject.Inject;
import java.util.List;

@Component
public class CitationManager {
	@Inject
	private CitationDAO citationDAO;
	@Inject
	private ViolationManager violationManager;

	public List<Citation> findCitations(CitationSearchCriteria criteria) {
		// Search by DOB & citation number
		if (criteria.dateOfBirth != null && criteria.citationNumber != null) {
			List<Citation> citations = citationDAO.getByCitationNumberAndDOB(criteria.citationNumber, criteria.dateOfBirth);
			return populateViolations(citations);
		}
		
		// DOB & License No
		if (criteria.dateOfBirth != null && criteria.driversLicenseNumber != null) {
			List<Citation> citations = citationDAO.getByLicenseAndDOB(criteria.driversLicenseNumber, criteria.dateOfBirth);
			return populateViolations(citations);
		}
		
		// DOB & Name & Municipality
		if (criteria.dateOfBirth != null && criteria.lastName != null && criteria.municipalities != null && criteria.municipalities.size() != 0) {
			List<Citation> citations =  citationDAO.getByNameAndMunicipalitiesAndDOB(criteria.lastName, criteria.municipalities, criteria.dateOfBirth);
			return populateViolations(citations);
		}
		
		LogSystem.LogEvent("Not enough information was passed as criteria to find citations");
		return Lists.newArrayList();
	}
	
	private List<Citation> populateViolations(List<Citation> citations) {
		if (citations == null) {
			return null;
		}
		
		for (Citation citation:citations) {
			citation.violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
		}
		return citations;
	}
}