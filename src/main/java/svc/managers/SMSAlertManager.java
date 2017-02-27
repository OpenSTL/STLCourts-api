package svc.managers;


import java.time.LocalDate;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import svc.data.textMessages.SMSAlertDAO;

@Component
public class SMSAlertManager {
	@Inject
	private SMSAlertDAO smsAlertsDAO;
	
	public boolean add(String citationNumber, String phoneNumber, LocalDate dob){
		return smsAlertsDAO.add(citationNumber, phoneNumber, dob);
	}
	
	public void sendAlerts(){
		System.out.println("Alert");
	}
}
