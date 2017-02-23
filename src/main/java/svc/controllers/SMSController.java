package svc.controllers;

import java.io.IOException;

import com.twilio.Twilio;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.logging.LogSystem;
import svc.managers.*;
import svc.models.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/sms")
public class SMSController {
	@Inject
	SMSManager smsManager;	
	
	@Value("${spring.accountSid}")
	String accountSid;
	
	@Value("${spring.authToken}")
	String authToken;
		
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	void GetMessage(@ModelAttribute TwimlMessageRequest twimlMessageRequest, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		session.setMaxInactiveInterval(30*60); //set session timeout to 30 minutes
		
		MessagingResponse twimlResponse = smsManager.getTwimlResponse(twimlMessageRequest, request, session);

	    response.setContentType("application/xml");

	    try {
	    	response.getWriter().print(twimlResponse.toXml());
	    } catch (TwiMLException e) {
	    	e.printStackTrace();
	    }
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/send")
	void SendMessage() {
		Twilio.init(accountSid,authToken);
		
		Message message = Message
				.creator(new PhoneNumber("+13145608699"), new PhoneNumber("+13142548050"),"My Test Message")
				.create();
	}
	
		
}
