package svc.controllers;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import svc.dto.CitationsDTO;
import svc.managers.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/SMSAlert")
public class SMSAlertController {	
	@Inject
	SMSAlertManager smsAlertManager;

	@Value("${twilio.accountSid}")
	String accountSid;
	
	@Value("${twilio.authToken}")
	String authToken;
	
	@Value("${twilio.phoneNumber}")
	String twilioPhone;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/add")
	boolean Add(@RequestParam(value = "citationNumber", required = true) String citationNumber,
        @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
        @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate dob) {
		
		
		return true;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/remove")
	boolean Remove(@RequestParam(value = "citationNumber", required = true) String citationNumber,
        @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
        @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate dob) {
		
		
		return true;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/send")
	void SendMessage() {
		Twilio.init(accountSid,authToken);
		
		Message.creator(new PhoneNumber("+13145608699"), new PhoneNumber(twilioPhone),"My Test Message").create();
	}

}
