package svc.managers;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import svc.data.citations.CitationDAO;
import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.models.Citation;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Component
public class CitationManager {
	@Inject
	private CitationDAO citationDAO;
	@Inject
	private ViolationManager violationManager;

	
	public Citation getCitationById(Long citationId) {
		Citation citation = citationDAO.getByCitationId(citationId);
		if (citation != null) {
			citation.violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
			LogSystem.LogEvent("Loaded " + citation.violations.size() + " violation(s) for this citation.");
		}
		return citation;
	}

	public List<Citation> findCitations(CitationSearchCriteria criteria) {
		// Search by DOB & citation number
		if (criteria.dateOfBirth != null && criteria.citationNumber != null) {
			List<Citation> citations = new ArrayList<Citation>();
			Citation citation = citationDAO.getByCitationNumberAndDOB(criteria.citationNumber, criteria.dateOfBirth);
			if (citation != null) {
				citations.add(citation);
			}
			return populateViolations(citations);
		}
		
		// DOB & License No
		if (criteria.dateOfBirth != null && criteria.driversLicenseNumber != null) {
			List<Citation> citations = citationDAO.getByDOBAndLicense(criteria.dateOfBirth, criteria.driversLicenseNumber);
			return populateViolations(citations);
		}
		
		// DOB & Name & Municipality
		if (criteria.dateOfBirth != null && criteria.lastName != null && criteria.municipalities != null && criteria.municipalities.size() != 0) {
			List<Citation> citations =  citationDAO.getByDOBAndNameAndMunicipalities(criteria.dateOfBirth, criteria.lastName, criteria.municipalities);
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
	
	public boolean insertDemoCitations(List<Citation> citations){
		return citationDAO.insertDemoCitations(citations);
	}
	
	public boolean removeDemoCitations(List<Citation> citations){
		return citationDAO.removeDemoCitations(citations);
	}
}