package svc.data.textMessages;


import javax.inject.Inject;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import svc.managers.SMSAlertManager;

@Component
public class SMSAlertTaskScheduler {
	@Inject
	SMSAlertManager smsAlertManager;
	
	@Inject
	SMSNotifier smsNotificationMessageSender;
	
	//http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
	//8AM every day
	@Scheduled(cron="0 0 8 * * *", zone="America/Chicago")
	@Async
	public void sendAlerts(){
		smsAlertManager.removeExpiredAlerts();
		smsNotificationMessageSender.sendAlerts();
	}
}
