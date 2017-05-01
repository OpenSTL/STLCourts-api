package svc.data.textMessages;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import svc.managers.SMSAlertManager;
import svc.models.SMSAlertNotification;

@Component
public class SMSNotifier {
	@Inject
	SMSAlertManager smsAlertManager;
	
	@Autowired
	private TwilioConfiguration twilioConfiguration;
	
	
	public void sendAlerts() {
		List<SMSAlertNotification> notificationsToSend = smsAlertManager.getAlertMessagesToSend();
		createAndSendMessages(notificationsToSend);
	}
	
	public void sendAlerts(String citationNumber, String phoneNumberToSendTo){
		List<SMSAlertNotification> notificationsToSend = smsAlertManager.getAlertMessagesToSend(citationNumber, phoneNumberToSendTo);
		createAndSendMessages(notificationsToSend);
	}
	
	private void createAndSendMessages(List<SMSAlertNotification> notificationsToSend){
		for(int notificationCount = 0; notificationCount < notificationsToSend.size(); notificationCount++){
			SMSAlertNotification notification = notificationsToSend.get(notificationCount);
			Twilio.init(twilioConfiguration.accountSid,twilioConfiguration.authToken);
			Message.creator(new PhoneNumber(notification.defendantPhone), new PhoneNumber(twilioConfiguration.phoneNumber),notification.message).create();
		}
	}
}
