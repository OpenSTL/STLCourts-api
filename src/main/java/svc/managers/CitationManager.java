package svc.managers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.citations.CitationDAO;
import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Violation;

@Component
public class CitationManager {
	@Inject
	private CitationDAO citationDAO;
	@Inject
	private ViolationManager violationManager;
	@Inject
	private CourtManager courtManager;
	@Inject
	private SMSManager smsManager;

	
	public Citation GetCitationById(Long citationId) {
		Citation citation = citationDAO.getByCitationId(citationId);
		if (citation != null) {
			citation.violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
			LogSystem.LogEvent("Loaded " + citation.violations.size() + " violation(s) for this citation.");
		}
		return citation;
	}

	public List<Citation> FindCitations(CitationSearchCriteria criteria) {
		// Search by DOB & citation number
		if (criteria.date_of_birth != null && criteria.citation_number != null) {
			List<Citation> citations = new ArrayList<Citation>();
			Citation citation = citationDAO.getByCitationNumberAndDOB(criteria.citation_number, criteria.date_of_birth);
			if (citation != null) {
				citations.add(citation);
			}
			return PopulateViolations(citations);
		}
		
		// DOB & License No
		if (criteria.date_of_birth != null && criteria.drivers_license_number != null) {
			List<Citation> citations = citationDAO.getByDOBAndLicense(criteria.date_of_birth, criteria.drivers_license_number);
			return PopulateViolations(citations);
		}
		
		// DOB & Name & Municipality
		if (criteria.date_of_birth != null && criteria.last_name != null && criteria.municipalities != null && criteria.municipalities.size() != 0) {
			List<Citation> citations =  citationDAO.getByDOBAndNameAndMunicipalities(criteria.date_of_birth, criteria.last_name, criteria.municipalities);
			citations = PopulateViolations(citations);
			return citations;
		}
		
		LogSystem.LogEvent("Not enough information was passed as crtieria to find citations");
		return null;
	}
	
	private List<Citation> PopulateViolations(List<Citation> citations) {
		if (citations == null) {
			return null;
		}
		
		for (Citation citation:citations) {
			citation.violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
		}
		return citations;
	}
	
	public String getCitationSMS(Citation citation){
		String message = "";
		message += "Ticket Date: "+smsManager.convertDatabaseDateToUS(citation.citation_date);
		message += "\nCourt Date: "+smsManager.convertDatabaseDateToUS(citation.court_date);
		message += "\nTicket #: "+citation.citation_number;
		Court court = courtManager.GetCourtById(Long.valueOf(citation.court_id));
		message += "\nCourt Address: "+court.address+" "+court.city+", "+court.state+" "+court.zip_code;
		List<Violation> violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
		int violationCount = 0;
		for(Violation violation:violations){
			if (violationCount > 0){
				message += "\n------------------";
			}
			message += violationManager.getViolationSMS(violation, false);
			violationCount++;
		}
		
		return message;
	}
}
