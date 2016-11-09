package svc.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import svc.dto.CitationSearchCriteria;
import svc.logging.LogSystem;
import svc.managers.*;
import svc.managers.SMSManager.SMS_STAGE;
import svc.models.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/sms")
public class SMSController {
	@Inject
	SMSManager smsManager;
	@Inject
	CitationManager citationManager;
	
	String dob,license;
	SimpleDateFormat dateFormat;
	CitationSearchCriteria criteria;
	List<Citation> citations;
	
	public static final String ACCOUNT_SID = "AC5d1ac1d852d5ee86bdb7e92bedc524ab";
	public static final String AUTH_TOKEN = "a636e8943be534b338147adfe6a8ba01";
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	void GetMessage(@ModelAttribute TwimlMessageRequest twimlMessageRequest, HttpServletResponse response, HttpSession session) throws IOException{
		session.setMaxInactiveInterval(30*60); //set session timeout to 30 minutes
		SMS_STAGE currentTextStage, nextTextStage;
		
		Integer stageNumber = (Integer) session.getAttribute("stage");
		if (stageNumber == null){
			currentTextStage = SMS_STAGE.WELCOME;
		}else{
			try{
				currentTextStage = SMS_STAGE.values()[stageNumber];
			}catch (IndexOutOfBoundsException iobe){
				currentTextStage = SMS_STAGE.WELCOME;
			}
		}
		nextTextStage = null;
		String message = "";
		switch(currentTextStage){
		case WELCOME:
			message = "Welcome to www.yourSTLcourts.com.  Please enter your birthdate using MM/DD/YYYY";
			nextTextStage = SMS_STAGE.READ_DOB;
			break;
		case READ_DOB:
			message = smsManager.validDOB(twimlMessageRequest.getBody());
			if (message == ""){
				session.setAttribute("dob", twimlMessageRequest.getBody().trim());
				message = "Thank you.  Now please enter your driver\'s license number.";
				nextTextStage = SMS_STAGE.READ_LICENSE;
			}else{
				nextTextStage = currentTextStage;
			}
			break;
		case READ_LICENSE:
			dob = (String)session.getAttribute("dob");
			
			criteria = new CitationSearchCriteria();
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try{
				criteria.date_of_birth = dateFormat.parse(dob);
				criteria.drivers_license_number = twimlMessageRequest.getBody().trim();
				session.setAttribute("license_number", twimlMessageRequest.getBody().trim());
				
				citations = citationManager.FindCitations(criteria);
				
				String ticketWord = (citations.size()>1)?" tickets were ":" ticket was ";
				if (citations.size() > 0){
					message = citations.size()+ticketWord+"found";
					
					int counter = 1;
					for(Citation citation : citations){
						message += "\n"+counter+") ticket from: "+smsManager.convertDatabaseDateToUS(citation.citation_date);
						counter++;
					}
					message += "\nReply with the ticket number you want to view.";
					nextTextStage = SMS_STAGE.VIEW_CITATION;
					
				}else{
					message = "No tickets were found.";
					nextTextStage = SMS_STAGE.WELCOME;
				}
			}catch (ParseException e){
				//something went wrong here  this shouldn't happen since dob has already been parsed
				message = "Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.";
				nextTextStage = SMS_STAGE.WELCOME;
			}
			break;
		case VIEW_CITATION:
			dob = (String)session.getAttribute("dob");
			license = (String)session.getAttribute("license_number");
			CitationSearchCriteria criteria = new CitationSearchCriteria();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try{
				criteria.date_of_birth = dateFormat.parse(dob);
				criteria.drivers_license_number = license;
				citations = citationManager.FindCitations(criteria);
				String citationNumber = twimlMessageRequest.getBody().trim();
				int citationNumberToView = Integer.parseInt(citationNumber) - 1;
				if (citationNumberToView >= 0 && citationNumberToView < citations.size()){
					Citation citationToView = citations.get(citationNumberToView);	
					message = citationManager.getCitationSMS(citationToView);
					nextTextStage = SMS_STAGE.WELCOME;  //could change this to go on and ask if want alerts for court dates
					
				}else{
					message = "Invalid entry. Please enter only the number of the ticket you would like to view.";
					nextTextStage = SMS_STAGE.VIEW_CITATION;
				}
				
				
			}catch (ParseException e){
				//something went wrong here  this shouldn't happen since dob has already been parsed
				message = "Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.";
				nextTextStage = SMS_STAGE.WELCOME;
			}catch (NumberFormatException ne){
				message = "Invalid entry. Please enter only the number of the ticket you would like to view.";
				nextTextStage = SMS_STAGE.VIEW_CITATION;
			}
			break;
		}
		
	    session.setAttribute("stage", new Integer(nextTextStage.ordinal()));
	    
	    MessagingResponse twimlResponse = smsManager.getTwimlResponse(message);

	    response.setContentType("application/xml");

	    try {
	    	response.getWriter().print(twimlResponse.toXml());
	    } catch (TwiMLException e) {
	      e.printStackTrace();
	    }
	}
	
		
}
