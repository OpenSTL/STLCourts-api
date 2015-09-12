package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.ViolationDAO;
import svc.models.Violation;

@Component
public class ViolationManager
{
	@Inject
	private ViolationDAO _violationDAO;
	
	public ViolationManager()
	{
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
