package svc.data.textMessages;

import java.util.List;

import svc.models.*;
import svc.util.DatabaseUtilities;

public class CitationTextMessage {
	private Citation citation;
	private Court court;
	List<Violation> violations;
	
	public CitationTextMessage(Citation citation, List<Violation> violations, Court court){
		this.citation = citation;
		this.violations = violations;
		this.court = court;
	}
	
	public String toTextMessage(){
		String message = "";
	
		message += "Ticket Date: " + DatabaseUtilities.convertDatabaseDateToUS(this.citation.citation_date);
		message += "\nCourt Date: " + DatabaseUtilities.convertDatabaseDateToUS(this.citation.court_date);
		message += "\nCourt Time: " + DatabaseUtilities.convertDatabaseDateToUSTime(this.citation.court_date);
		message += "\nTicket #: " + this.citation.citation_number;
		message += "\nCourt Address: "+court.address+" "+court.city+", "+court.state+" "+court.zip;
		int violationCount = 0;
		for(Violation violation:violations){
			if (violationCount > 0){
				message += "\n------------------";
			}
			message += getViolationSMS(violation, false);
			violationCount++;
		}
		if (violationCount == 0)
			message += "\nNo Violations were found for this ticket.";
		
		return message;
	}
	
	
	private String getViolationSMS(Violation violation, boolean showDismissedViolations){
		String message = "";
		//by default Closed violations will not be returned.
		if ((violation.status != VIOLATION_STATUS.CLOSED) && (violation.status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS || showDismissedViolations)){
			message += "\nViolation #: "+violation.violation_number;
			message += "\nViolation: "+violation.violation_description;
			message += "\nStatus (as of "+DatabaseUtilities.convertDatabaseDateToUS(violation.status_date)+"): "+violation.status.toString();
			if (violation.status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS){
				message += "\nFine Amount: $"+violation.fine_amount;
				message += "\nCourt Costs: $"+violation.court_cost;
			}
		}
		
		return message;
	}
	
}
