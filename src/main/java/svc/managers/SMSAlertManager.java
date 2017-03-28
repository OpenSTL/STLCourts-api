package svc.managers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import svc.data.textMessages.SMSAlertDAO;
import svc.data.textMessages.SMSAlertMessageCreator;
import svc.dto.CitationSearchCriteria;
import svc.models.Citation;
import svc.models.Court;
import svc.models.SMSAlert;
import svc.models.SMSAlertNotification;
import svc.util.DatabaseUtilities;

@Component
public class SMSAlertManager {
	@Inject
	private SMSAlertDAO smsAlertsDAO;
	
	@Inject
	private CitationManager citationManager;
	
	@Inject
	private CourtManager courtManager;
	
	@Value("${stlcourts.clientURL}")
	String clientURL;
	
	public boolean add(String citationNumber, LocalDateTime courtDateTime, String phoneNumber, LocalDate dob){
		return smsAlertsDAO.add(citationNumber, courtDateTime, phoneNumber, dob);
	}
	
	public boolean remove(String citationNumber, String phoneNumber, LocalDate dob){
		return smsAlertsDAO.remove(citationNumber, phoneNumber, dob);
	}
	
	public void removeExpiredAlerts(){
		smsAlertsDAO.removeExpiredAlerts();
	}
	
	public List<SMSAlertNotification> getAlertMessagesToSend(){
		List<SMSAlertNotification> notificationsToSend = new ArrayList<SMSAlertNotification>();
		List<SMSAlert> dailyAlerts = smsAlertsDAO.getDailyAlerts();
		for (int alertCount = 0; alertCount < dailyAlerts.size(); alertCount++){
			SMSAlert dailyAlert = dailyAlerts.get(alertCount);
			CitationSearchCriteria criteria = new CitationSearchCriteria();
			criteria.dateOfBirth = dailyAlert.dob;
			criteria.citationNumber = dailyAlert.citationNumber;
			List<Citation> citations = citationManager.findCitations(criteria);
			Court court = courtManager.getCourtById(citations.get(0).court_id.getValue());
			String link = clientURL+"/paymentOptions/"+citations.get(0).citation_number;
			
			boolean canSendMessage = true;
			if (!isAlertDateStillCurrent(dailyAlert.courtDate,citations.get(0))){
				updateSMSAlertWithUpdatedCourtDate(dailyAlert,citations.get(0).court_dateTime);
				canSendMessage = doesAlertMeetSendingRequirments(dailyAlert);
			}
			
			if (canSendMessage){
				SMSAlertNotification notification = SMSAlertMessageCreator.getMessage(dailyAlert,citations.get(0),court,link);
				notificationsToSend.add(notification);
			}
		}
		
		return notificationsToSend;
	}
	
	private boolean isAlertDateStillCurrent(LocalDateTime alertDate, Citation citation){
		return alertDate.toString().equals(citation.court_dateTime.toString());
	}
	
	private void updateSMSAlertWithUpdatedCourtDate(SMSAlert dailyAlert, LocalDateTime updatedCourtDate){
		dailyAlert.courtDate = updatedCourtDate;
		smsAlertsDAO.updateSMSAlertWithUpdatedCourtDate(dailyAlert);
	}
	
	private boolean doesAlertMeetSendingRequirments(SMSAlert dailyAlert){
		Period daysTillCourt = DatabaseUtilities.getCurrentDate().until(dailyAlert.courtDate.toLocalDate());
		if (daysTillCourt.getDays() == 1 || daysTillCourt.getDays() == 7 || daysTillCourt.getDays() == 14){
			return true;
		}else{
			return false;
		}	
	}
}
