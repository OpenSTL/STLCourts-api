package svc.managers;

import svc.data.CitationDAO;
import svc.logging.LogSystem;
import svc.models.Citation;

public class CitationManager
{
	private CitationDAO _citationDAO = null;
	private ViolationManager _violationManager = null;
	
	public CitationManager(CitationDAO citationDAO, ViolationManager violationManager)
	{
		_citationDAO = citationDAO;
		_violationManager = violationManager;
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

}
