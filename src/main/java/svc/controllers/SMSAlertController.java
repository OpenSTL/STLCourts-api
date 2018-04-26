package svc.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.managers.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/SMSAlert")
public class SMSAlertController {	
	@Inject
	SMSAlertManager smsAlertManager;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/add")
	boolean Add(@RequestParam(value = "citationNumber", required = true) String citationNumber,
        @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
        @RequestParam(value = "courtDateTime", required = true) @DateTimeFormat(pattern="MM/dd/yyyy HH:mm") LocalDateTime courtDateTime,
        @RequestParam(value = "zoneId", required = true) String zoneId,
        @RequestParam(value = "dob", required = true) @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate dob) {
		
		smsAlertManager.add(citationNumber, courtDateTime, zoneId, phoneNumber, dob);
		return true;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value="/remove")
	boolean Remove(@RequestParam(value = "citationNumber", required = true) String citationNumber,
        @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
        @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern="MM/dd/yyyy") LocalDate dob) {
		
		smsAlertManager.remove(citationNumber, phoneNumber, dob);
		return true;
	}
	
}
