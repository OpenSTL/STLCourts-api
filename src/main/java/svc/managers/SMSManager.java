package svc.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;

import svc.dto.CitationSearchCriteria;
import svc.managers.ViolationManager.VIOLATION_STATUS;
import svc.models.Citation;
import svc.models.Court;
import svc.models.TwimlMessageRequest;
import svc.models.Violation;

@Component
public class SMSManager {
	@Inject
	CitationManager citationManager;
	@Inject
	private CourtManager courtManager;
	@Inject
	private ViolationManager violationManager;
	
	private enum SMS_STAGE{
		WELCOME(0),
		READ_DOB(1),
		READ_LICENSE(2),
		VIEW_CITATION(3);
		
		private int numVal;
		
		SMS_STAGE(int numVal){
			this.numVal = numVal;
		}
		
		public int getNumVal(){
			return numVal;
		}
	}
	
	
	//a null String means validDOB, otherwise a message is returned to pass on to user
	private String validDOB(String dob){
		String errMsg = "";
		dob = dob.trim();
		if (!dob.matches("^\\d{2}([./-])\\d{2}\\1\\d{4}$")){
			errMsg = "Please re-enter your birthdate.  The format is: MM/DD/YYYY";
		}else{
			if (isValidDate(dob)){
				//Check that the DOB is > 18 years old
				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
				DateTime dobDT = formatter.parseDateTime(dob);
				DateTime now = new DateTime();
				Years age = Years.yearsBetween(dobDT, now);
				if (age.getYears() < 18){
					errMsg = "You must be at leat 18 years old to use www.yourSTLcourts.com";
				}
			}else{
				//DOB is not a valid date
				errMsg = "The date you entered: '"+dob+"' was not a valid date.  Please re-enter your birthdate using MM/DD/YYYY";
			}
		}
		
		return errMsg;
	}
	
	
	private boolean isValidDate(String dateToCheck){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(dateToCheck.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	}
	
	private String convertDatabaseDateToUS(Date databaseDate){
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		return usFormat.format(databaseDate);
	}
	
	private String getViolationSMS(Violation violation, boolean showDismissedViolations){
		String message = "";
		//by default Closed violations will not be returned.
		VIOLATION_STATUS status = VIOLATION_STATUS.valueOf(violation.status.replaceAll(" ", "_"));
		if ((status != VIOLATION_STATUS.CLOSED) && (status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS || showDismissedViolations)){
			message += "\nViolation #: "+violation.violation_number;
			message += "\nViolation: "+violation.violation_description;
			message += "\nStatus (as of "+convertDatabaseDateToUS(violation.status_date)+"): "+status.toString();
			if (status != VIOLATION_STATUS.DISMISS_WITHOUT_COSTS){
				message += "\nFine Amount: $"+violation.fine_amount;
				message += "\nCourt Costs: $"+violation.court_cost;
			}
		}
		return message;
	}
	
	public String getCitationSMS(Citation citation){
		String message = "";
		message += "Ticket Date: "+convertDatabaseDateToUS(citation.citation_date);
		message += "\nCourt Date: "+convertDatabaseDateToUS(citation.court_date);
		message += "\nTicket #: "+citation.citation_number;
		Court court = courtManager.GetCourtById(Long.valueOf(citation.court_id));
		message += "\nCourt Address: "+court.address+" "+court.city+", "+court.state+" "+court.zip_code;
		List<Violation> violations = violationManager.getViolationsByCitationNumber(citation.citation_number);
		int violationCount = 0;
		for(Violation violation:violations){
			if (violationCount > 0){
				message += "\n------------------";
			}
			message += getViolationSMS(violation, false);
			violationCount++;
		}
		
		return message;
	}
	
	private MessagingResponse createTwimlResponse(String msg){
		Message sms = new Message.Builder().body(new Body(msg)).build();
	    MessagingResponse twimlResponse = new MessagingResponse.Builder().message(sms).build();
	    return twimlResponse;
	}
	
	public MessagingResponse getTwimlResponse(TwimlMessageRequest twimlMessageRequest, HttpSession session){
		SMS_STAGE currentTextStage, nextTextStage;
		String dob,license;
		SimpleDateFormat dateFormat;
		CitationSearchCriteria criteria;
		List<Citation> citations;
		
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
			message = validDOB(twimlMessageRequest.getBody());
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
						message += "\n"+counter+") ticket from: "+convertDatabaseDateToUS(citation.citation_date);
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
			criteria = new CitationSearchCriteria();
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try{
				criteria.date_of_birth = dateFormat.parse(dob);
				criteria.drivers_license_number = license;
				citations = citationManager.FindCitations(criteria);
				String citationNumber = twimlMessageRequest.getBody().trim();
				int citationNumberToView = Integer.parseInt(citationNumber) - 1;
				if (citationNumberToView >= 0 && citationNumberToView < citations.size()){
					Citation citationToView = citations.get(citationNumberToView);	
					message = getCitationSMS(citationToView);
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
	    
	    MessagingResponse twimlResponse = createTwimlResponse(message);
	    
	    return twimlResponse;
	}

}
