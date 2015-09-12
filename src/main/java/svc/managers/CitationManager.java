package svc.managers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.CitationDAO;
import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.models.Citation;

@Component
public class CitationManager
{
	@Inject
	private CitationDAO _citationDAO;
	
	@Inject
	private ViolationManager _violationManager;
	
	public CitationManager()
	{
	}
	
	public Citation GetCitationById(int citationId)
	{
		Citation citation = _citationDAO.getByCitationId(citationId);
		if (citation != null)
		{
			citation.violations = _violationManager.getViolationsByCitationNumber(citation.citation_number);
			LogSystem.LogEvent("Loaded " + citation.violations.size() + " violation(s) for this citation.");
		}
		return citation;
	}

	public List<Citation> FindCitations(CitationSearchCriteria criteria)
	{
		// Search by citation number
		if (criteria.citation_number != null)
		{
			LogSystem.LogEvent("Searching for citations by citation number...");
			List<Citation> citations = new ArrayList<Citation>();
			Citation citation = _citationDAO.getByCitationNumber(criteria.citation_number);
			if (citation != null)
			{
				citations.add(citation);
			}
			return citations;
		}
		
		// DOB & License No
		if (criteria.date_of_birth != null && criteria.drivers_license_number != null)
		{
			LogSystem.LogEvent("Searching for citations by DOB & license number...");
			return _citationDAO.getByDOBAndLicense(criteria.date_of_birth, criteria.drivers_license_number);
		}
		
		// DOB & Name & Municipality
		if (criteria.date_of_birth != null && criteria.first_name != null &&
			criteria.last_name != null && criteria.municipalities != null && criteria.municipalities.size() != 0)
		{
			LogSystem.LogEvent("Searching for citations by DOB & Name & Municipality...");
			return _citationDAO.getByDOBAndNameAndMunicipalities(criteria.date_of_birth,
																 criteria.first_name,
																 criteria.last_name,
																 criteria.municipalities);
		}
		
		LogSystem.LogEvent("Not enough information was passed as crtieria to find citations");
		return null;
	}

}
