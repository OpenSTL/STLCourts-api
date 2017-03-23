package svc.data.textMessages;

import java.time.Period;

import svc.models.Citation;
import svc.models.Court;
import svc.models.SMSAlert;
import svc.models.SMSAlertNotification;
import svc.util.DatabaseUtilities;

public class SMSAlertMessageCreator {

	public static SMSAlertNotification getMessage(SMSAlert dailyAlert, Citation citation, Court court, String additionalInfoLink){
		String message = "Court Date Reminder\n";
		Period daysTillCourt = DatabaseUtilities.getCurrentDate().until(citation.court_dateTime.toLocalDate());
		String courtTime = DatabaseUtilities.convertDatabaseDateToUSTime(citation.court_dateTime);
		if (daysTillCourt.getDays() == 1){
			message += "You have court tomorrow at: "+courtTime+"\n\n";
		}else{
			String courtDate = DatabaseUtilities.convertDatabaseDateToUS(citation.court_dateTime);
			message += "You have a court date coming up on: "+courtDate+" at: "+courtTime+"\n\n";
		}
		
		message += "Location:\n";
		message += court.address+"\n";
		message += court.city+", "+court.state+" "+court.zip+"\n";
		
		message += "For more information about your citation, legal rights, how to dress, etc, please visit: \n"+additionalInfoLink;
		
		SMSAlertNotification notification = new SMSAlertNotification();
		notification.defendantPhone = "+"+dailyAlert.defendantPhoneNumber;
		notification.message = message;
		
		return notification;
	}
	
}
