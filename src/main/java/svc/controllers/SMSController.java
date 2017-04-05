package svc.controllers;

import java.io.IOException;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;
import svc.models.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/sms")
public class SMSController {
	@Inject
	SMSManager smsManager;	
	
		
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
	@RequestMapping(method = RequestMethod.GET, value="/info")
	SMSInfo GetPhoneNumber() throws IOException{
		return smsManager.getInfo();
	}
		
}
