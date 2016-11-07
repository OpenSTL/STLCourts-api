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
	@Inject
	private SMSManager smsManager;
	
	private enum VIOLATION_STATUS{
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
	
	public String getViolationSMS(Violation violation, boolean showDismissedViolations){
		String message = "";
		//by default Closed violations will not be returned.
		VIOLATION_STATUS status = VIOLATION_STATUS.valueOf(violation.status.replaceAll(" ", "_"));
		if ((status != VIOLATION_STATUS.CLOSED) && (status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS || showDismissedViolations)){
			message += "\nViolation #: "+violation.violation_number;
			message += "\nViolation: "+violation.violation_description;
			message += "\nStatus (as of "+smsManager.convertDatabaseDateToUS(violation.status_date)+"): "+status.toString();
			if (status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS){
				message += "\nFine Amount: $"+violation.fine_amount;
				message += "\nCourt Costs: $"+violation.court_cost;
			}
		}
		return message;
	}
}
