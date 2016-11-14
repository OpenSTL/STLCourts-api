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
	
	public enum VIOLATION_STATUS{
		CONT_FOR_PAYMENT,
		FTA_WARRANT_ISSUED,
		DISMISS_WITHOUT_COSTS,
		CLOSED;
		
		
		@Override
		public String toString(){
			String s = "";
			if (this.ordinal() == 0){
				s = "Continued for payment";
			}else{
				if (this.ordinal() == 1){
					s = "An arrest warrent has been issued for failure to appear to your court date";
				}else{
					if (this.ordinal() == 2){
						s = "Dismissed";
					}else{
						if (this.ordinal() == 3){
							s = "Closed";
						}
					}
				}
			}
			return s;
		}
	}
		
	public Violation GetViolationById(int violationId) {
		return violationDAO.getViolationDataById(violationId);
	}

	public List<Violation> getViolationsByCitationNumber(String citationNumber) {
		return violationDAO.getViolationsByCitationNumber(citationNumber);
	}
	
}
