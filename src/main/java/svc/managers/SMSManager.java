package svc.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;

import svc.dto.CitationSearchCriteria;
import svc.models.Citation;
import svc.models.Court;
import svc.models.TwimlMessageRequest;
import svc.models.Violation;
import svc.util.DatabaseUtilities;
import svc.data.textMessages.CitationTextMessage;
import svc.data.textMessages.ListCitationsTextMessage;

@Component
public class SMSManager {
	@Inject
	CitationManager citationManager;
	@Inject
	CourtManager courtManager;
	@Inject
	ViolationManager violationManager;
	
	@Value("${spring.clientURL}")
	String clientURL;
	
	private enum SMS_STAGE{
		WELCOME(0),
		READ_DOB(1),
		READ_LICENSE(2),
		VIEW_CITATION(3),
		READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN(4);
		
		private int numVal;
		
		SMS_STAGE(int numVal){
			this.numVal =numVal;
		}
	
		public int getNumVal() {
			return numVal;
		}
	}
	
	
	//a empty String means validDOB, otherwise a message is returned to pass on to user
	private String validateDOBString(String dob){
		String errMsg = "";
		dob = dob.trim();
		if (!dob.matches("^\\d{2}([./-])\\d{2}\\1\\d{4}$")){
			//makes sure DOB is a valid date string format, also accepts "." and "-"
			errMsg = "You entered: '" + dob + "' Please re-enter your birthdate.  The format is: MM/DD/YYYY";
		}else{
			//be forgiving for dates delineated with "." or "-"
			dob = dob.replaceAll("[.-]","/");
			if (!DatabaseUtilities.isStringValidUSDateString(dob)){
				//DOB is not a valid date, meaning month is out of bounds or day of month does not align with month or year
				errMsg = "The date you entered: '"+dob+"' was not a valid date.  Please re-enter your birthdate using MM/DD/YYYY";
			}else{
				//Check that the DOB is > 18 years old
				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
				DateTime dobDT = formatter.parseDateTime(dob);
				DateTime now = new DateTime();
				Years age = Years.yearsBetween(dobDT, now);
				if (age.getYears() < 18){
					errMsg = "You must be at least 18 years old to use www.yourSTLcourts.com";
				}
			}
		}	
		return errMsg;
	}
	
	
	private MessagingResponse createTwimlResponse(String msg){
		Message sms = new Message.Builder().body(new Body(msg)).build();
	    MessagingResponse twimlResponse = new MessagingResponse.Builder().message(sms).build();
	    return twimlResponse;
	}
	
	private SMS_STAGE getStageFromSession(HttpSession session){
		SMS_STAGE stage;
		
		Integer stageNumber = (Integer) session.getAttribute("stage");
		if (stageNumber == null){
			stage = SMS_STAGE.WELCOME;
		}else{
			try{
				stage = SMS_STAGE.values()[stageNumber];
			}catch (IndexOutOfBoundsException iobe){
				stage = SMS_STAGE.WELCOME;
			}
		}
		
		return stage;
	}
	
	private void setNextStageInSession(HttpSession session, SMS_STAGE nextTextStage){
		session.setAttribute("stage", new Integer(nextTextStage.getNumVal()));
	}
	
	private String generateWelcomeStageMessage(HttpSession session){
		String message = "Welcome to www.yourSTLcourts.com.  Please enter your birthdate using MM/DD/YYYY";
		setNextStageInSession(session,SMS_STAGE.READ_DOB);
		return message;
	}
	
	private String generateReadDobMessage(HttpSession session, String dob){
		SMS_STAGE nextTextStage;
		String message = validateDOBString(dob);
		if (message == ""){
			session.setAttribute("dob", dob);
			message = "Thank you.  Now please enter your driver\'s license number.";
			nextTextStage = SMS_STAGE.READ_LICENSE;
		}else{
			nextTextStage = SMS_STAGE.READ_DOB;
		}
		setNextStageInSession(session,nextTextStage);
		return message;
	}
	
	private String ListCitations(Date dob, String license){
		CitationSearchCriteria criteria = new CitationSearchCriteria();
		criteria.date_of_birth = dob;
		criteria.drivers_license_number = license;
		List<Citation> citations = citationManager.FindCitations(criteria);
		
		ListCitationsTextMessage listCitationsTM = new ListCitationsTextMessage(citations);
		
		String message = listCitationsTM.toTextMessage();
		
		return message;
	}
	
	private String generateReadLicenseMessage(HttpSession session, String licenseNumber){
		SimpleDateFormat dateFormat;
		String message = "";
		SMS_STAGE nextTextStage;
		
		licenseNumber = (licenseNumber != null)?licenseNumber:(String)session.getAttribute("license_number");
		String dob = (String)session.getAttribute("dob");
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		try{
			Date date_of_birth = dateFormat.parse(dob);
			session.setAttribute("license_number", licenseNumber);
			message = ListCitations(date_of_birth, licenseNumber);
			if (message == ""){
				message = "No tickets were found. If you have additional questions, go to www.yourSTLcourts.com.";
				nextTextStage = SMS_STAGE.WELCOME;
			}else{
				nextTextStage = SMS_STAGE.VIEW_CITATION;
			}
			
		}catch (ParseException e){
			//something went wrong here  this shouldn't happen since dob has already been parsed
			message = "Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.";
			nextTextStage = SMS_STAGE.WELCOME;
		}
		
		setNextStageInSession(session,nextTextStage);
		return message;
	}
	
	private String generateReadLicenseMessage(HttpSession session){
		String message = generateReadLicenseMessage(session, null);
		return message;
	}
	
	private String replyWithAdditionalViewingOptions(){
		String message = "";
		message += "\nReply with '1' to view another Ticket";
		message += "\nReply with '2' for payment Options";
		return message;
	}
	
	private String generateViewCitationsAgainMessage(HttpSession session, HttpServletRequest request, String menuChoice){
		String message = "";
		
		switch(menuChoice){
			case "1":
				message = generateReadLicenseMessage(session);
				break;
			case "2":
				message = "Visit ";
				message += clientURL+"/paymentOptions";
				
				String citation = (String)session.getAttribute("citation");
				message += "/"+citation;
				setNextStageInSession(session,SMS_STAGE.WELCOME);
				break;
			default:
				message = "Option not recognized.";
				message += replyWithAdditionalViewingOptions();
				setNextStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
				break;
		}
		
		return message;
	}
	
	private String generateViewCitationMessage(HttpSession session, String citationNumber){
		CitationSearchCriteria criteria;
		SimpleDateFormat dateFormat;
		List<Citation> citations;
		String message = "";
		SMS_STAGE nextTextStage;
		
		String dob = (String)session.getAttribute("dob");
		String license = (String)session.getAttribute("license_number");
		criteria = new CitationSearchCriteria();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try{
			criteria.date_of_birth = dateFormat.parse(dob);
			criteria.drivers_license_number = license;
			citations = citationManager.FindCitations(criteria);
			int citationNumberToView = Integer.parseInt(citationNumber) - 1;
			if (citationNumberToView >= 0 && citationNumberToView < citations.size()){
				Citation citationToView = citations.get(citationNumberToView);
				List<Violation> violations = violationManager.getViolationsByCitationNumber(citationToView.citation_number);
				Court court = courtManager.GetCourtById(Long.valueOf(citationToView.court_id));
				CitationTextMessage citationTextMessage = new CitationTextMessage(citationToView,violations,court);
				message = citationTextMessage.toTextMessage();
				message += replyWithAdditionalViewingOptions();
				session.setAttribute("citation", citationToView.citation_number);
				nextTextStage = SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN;
				
			}else{
				message = "Invalid entry. Please enter only the number of the ticket you would like to view.";
				nextTextStage = SMS_STAGE.VIEW_CITATION;
			}
			
			
		}catch (ParseException e){
			//something went wrong here  this shouldn't happen since dob has already been parsed
			message = "Sorry, something went wrong with your birthdate.  Please use the website www.yourSTLcourts.com.";
			nextTextStage = SMS_STAGE.WELCOME;
		}catch (NumberFormatException ne){
			message = "Invalid entry. Please enter only the number of the ticket you would like to view.";
			nextTextStage = SMS_STAGE.VIEW_CITATION;
		}catch (Exception exception){
			//message = "Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.";
			nextTextStage = SMS_STAGE.VIEW_CITATION;
		}
		
		setNextStageInSession(session,nextTextStage);
		return message;
	}
	
	public MessagingResponse getTwimlResponse(TwimlMessageRequest twimlMessageRequest, HttpServletRequest request, HttpSession session){
		SMS_STAGE currentTextStage = getStageFromSession(session);
		String message = "";
		String content = twimlMessageRequest.getBody().trim();
		
		switch(currentTextStage){
		case WELCOME:
			message = generateWelcomeStageMessage(session);
			break;
		case READ_DOB:
			message = generateReadDobMessage(session, content);
			break;
		case READ_LICENSE:
			message = generateReadLicenseMessage(session, content);
			break;
		case VIEW_CITATION:
			message = generateViewCitationMessage(session, content);
			break;
		case READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN:
			message = generateViewCitationsAgainMessage(session, request, content);
			break;
		}
	    
	    MessagingResponse twimlResponse = createTwimlResponse(message);
	    
	    return twimlResponse;
	}

}
