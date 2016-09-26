package svc.managers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.citations.ViolationDAO;
import svc.models.Violation;

@Component
public class ViolationManager {
	@Inject
	private ViolationDAO violationDAO;
	
	public Violation GetViolationById(int violationId) {
		return violationDAO.getViolationDataById(violationId);
	}

	public List<Violation> getViolationsByCitationNumber(String citationNumber) {
		return violationDAO.getViolationsByCitationNumber(citationNumber);
	}
}
