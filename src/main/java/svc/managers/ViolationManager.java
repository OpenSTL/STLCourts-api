package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.mock.ViolationDAO;
import svc.models.Violation;

@Component
public class ViolationManager {
	@Inject
	private ViolationDAO violationDAO;

	public List<Violation> getViolationsByCitationNumber(String citationNumber) {
		return violationDAO.getViolationsByCitationNumber(citationNumber);
	}
	
	public boolean insertViolations(List<Violation> violations){
		return violationDAO.insertViolations(violations);
	}
	
	public boolean removeViolations(List<Violation> violations){
		return violationDAO.removeViolations(violations);
	}
	
}
