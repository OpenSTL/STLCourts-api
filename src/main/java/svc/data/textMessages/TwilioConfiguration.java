package svc.data.textMessages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfiguration {

	@Value("${stlcourts.twilio.accountSid}")
	public String accountSid;
	
	@Value("${stlcourts.twilio.authToken}")
	public String authToken;
	
	@Value("${stlcourts.twilio.phoneNumber}")
	public String phoneNumber;
}
