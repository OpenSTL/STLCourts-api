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

	public List<Citation> findCitations(CitationSearchCriteria criteria) {
		// Search by DOB & citation number
		if (criteria.dateOfBirth != null && criteria.citationNumber != null) {
			return citationDAO.getByCitationNumberAndDOB(criteria.citationNumber, criteria.dateOfBirth);
		}
		
		// DOB & License No
		if (criteria.dateOfBirth != null && criteria.driversLicenseNumber != null) {
			return citationDAO.getByLicenseAndDOB(criteria.driversLicenseNumber, criteria.driversLicenseState, criteria.dateOfBirth);
		}
		
		// DOB & Name & Municipality
		if (criteria.dateOfBirth != null && criteria.lastName != null && criteria.municipalities != null && criteria.municipalities.size() != 0) {
			return  citationDAO.getByNameAndMunicipalitiesAndDOB(criteria.lastName, criteria.municipalities, criteria.dateOfBirth);
		}
		
		LogSystem.LogEvent("Not enough information was passed as criteria to find citations");
		return Lists.newArrayList();
	}
}