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
		// Search by DOB & citation number
		if (criteria.date_of_birth != null && criteria.citation_number != null)
		{
			List<Citation> citations = new ArrayList<Citation>();
			Citation citation = _citationDAO.getByCitationNumberAndDOB(criteria.citation_number, criteria.date_of_birth);
			if (citation != null)
			{
				citations.add(citation);
			}
			return PopulateViolations(citations);
		}
		
		// DOB & License No
		if (criteria.date_of_birth != null && criteria.drivers_license_number != null)
		{
			List<Citation> citations = _citationDAO.getByDOBAndLicense(criteria.date_of_birth, criteria.drivers_license_number);
			return PopulateViolations(citations);
		}
		
		// DOB & Name & Municipality
		if (criteria.date_of_birth != null &&
			criteria.last_name != null && criteria.municipalities != null && criteria.municipalities.size() != 0)
		{
			List<Citation> citations =  _citationDAO.getByDOBAndNameAndMunicipalities(criteria.date_of_birth,
																 criteria.last_name,
																 criteria.municipalities);
			
			citations = PopulateViolations(citations);
			return citations;
		}
		
		LogSystem.LogEvent("Not enough information was passed as crtieria to find citations");
		return null;
	}
	
	private List<Citation> PopulateViolations(List<Citation> citations)
	{
		if (citations == null)
		{
			return null;
		}
		
		for (Citation citation:citations)
		{
			citation.violations = _violationManager.getViolationsByCitationNumber(citation.citation_number);
		}
		return citations;
	}
}
