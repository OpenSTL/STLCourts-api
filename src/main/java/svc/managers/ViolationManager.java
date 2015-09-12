package svc.managers;

import java.util.List;

import svc.data.ViolationDAO;
import svc.models.Violation;

public class ViolationManager
{
	private ViolationDAO _violationDAO = null;
	
	public ViolationManager(ViolationDAO violationDAO)
	{
		_violationDAO = violationDAO;
	}
	
	public Violation GetViolationById(int violationId)
	{
		return _violationDAO.getViolationDataById(violationId);
	}

	public List<Violation> getViolationsByCitationNumber(String citationNumber) 
	{
		return _violationDAO.getViolationsByCitationNumber(citationNumber);
	}
}
