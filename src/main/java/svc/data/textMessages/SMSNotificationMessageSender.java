package svc.data.textMessages;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import svc.managers.SMSAlertManager;
import svc.models.SMSAlertNotification;

@Component
public class SMSNotificationMessageSender {
	@Inject
	SMSAlertManager smsAlertManager;
	
	@Value("${stlcourts.twilio.accountSid}")
	String accountSid;
	
	@Value("${stlcourts.twilio.authToken}")
	String authToken;
	
	@Value("${stlcourts.twilio.phoneNumber}")
	String twilioPhone;
	
	public void sendAlerts() {
		List<SMSAlertNotification> notificationsToSend = smsAlertManager.getAlertMessagesToSend();
		for(int notificationCount = 0; notificationCount < notificationsToSend.size(); notificationCount++){
			SMSAlertNotification notification = notificationsToSend.get(notificationCount);
			Twilio.init(accountSid,authToken);
			Message.creator(new PhoneNumber(notification.defendantPhone), new PhoneNumber(twilioPhone),notification.message).create();
		}
	}
}
