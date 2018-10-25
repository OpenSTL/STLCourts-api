package svc.managers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;

import svc.dto.CitationSearchCriteria;
import svc.models.Citation;
import svc.models.Court;
import svc.models.SMSInfo;
import svc.models.TwimlMessageRequest;
import svc.models.Violation;
import svc.util.DatabaseUtilities;
import svc.data.textMessages.CitationTextMessage;
import svc.data.textMessages.ListCitationsTextMessage;
import svc.data.textMessages.SMSNotifier;
import svc.data.textMessages.SMSSpamProtector;
import svc.data.textMessages.TwilioConfiguration;

@Component
public class SMSManager {
	@Inject
	CitationManager citationManager;
	@Inject
	CourtManager courtManager;
	@Inject
	ViolationManager violationManager;
	@Inject
	SMSAlertManager smsAlertManager;
	@Inject
	SMSNotifier smsNotifier;

	@Value("${stlcourts.clientURL}")
	String clientURL;

	@Autowired
	private TwilioConfiguration twilioConfiguration;

	private enum SMS_STAGE{
		WELCOME(0),
		READ_LASTNAME(1),
		READ_DOB(2),
		READ_LICENSE(3),
		READ_STATE(4),
		VIEW_CITATION(5),
		READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN(6);
		
		private int numVal;

		SMS_STAGE(int numVal){
			this.numVal =numVal;
		}

		public int getNumVal() {
			return numVal;
		}
	}

	public SMSInfo getInfo(){
		SMSInfo info = new SMSInfo();
		info.phoneNumber = twilioConfiguration.phoneNumber;
		return info;
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
				LocalDate dobLD = DatabaseUtilities.convertUSStringDateToLD(dob);
				LocalDate nowLD = DatabaseUtilities.getCurrentDate();
				Period age = dobLD.until(nowLD);
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
		String message = "Welcome to www.yourSTLcourts.com.  Please enter your last name";
		setNextStageInSession(session,SMS_STAGE.READ_LASTNAME);
		return message;
	}

	private String generateReadLastNameMessage(HttpSession session, String lastName){
		SMS_STAGE nextTextStage;
		lastName = (lastName != null)?lastName:(String)session.getAttribute("last_name");
		session.setAttribute("last_name", lastName);
		String message = "Thank you.  Now please enter your birthdate using MM/DD/YYYY";
		nextTextStage = SMS_STAGE.READ_DOB;
		setNextStageInSession(session,nextTextStage);
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
			message += "\n\n" + SMSSpamProtector.addError(session);
			nextTextStage = SMS_STAGE.READ_DOB;
		}
		setNextStageInSession(session,nextTextStage);
		return message;
	}

	private String ListCitations(LocalDate dob, String license, String licenseState, String lastName){
		CitationSearchCriteria criteria = new CitationSearchCriteria();
		criteria.dateOfBirth = dob;
		criteria.driversLicenseNumber = license;
		criteria.driversLicenseState = licenseState;
		criteria.lastName = lastName;
		List<Citation> citations = citationManager.findCitations(criteria);

		ListCitationsTextMessage listCitationsTM = new ListCitationsTextMessage(citations);

		String message = listCitationsTM.toTextMessage();

		return message;
	}

	private String generateReadLicenseMessage(HttpSession session, String licenseNumber){
		SMS_STAGE nextTextStage;
		licenseNumber = (licenseNumber != null)?licenseNumber:(String)session.getAttribute("license_number");
		session.setAttribute("license_number", licenseNumber);
		String message = "Thank you.  Now please enter your driver\'s license state, using the two letter abbreviation.  Examples.  Use \"MO\" for Missouri and \"IL\" for Illinois.";
		nextTextStage = SMS_STAGE.READ_STATE;
		setNextStageInSession(session,nextTextStage);
		return message;
	}

	private String generateReadStateMessage(HttpSession session){
		String message = generateReadStateMessage(session, null);
		return message;
	}

	private String generateReadStateMessage(HttpSession session, String licenseState){
		String message = "";
		SMS_STAGE nextTextStage;

		licenseState = (licenseState != null)?licenseState:(String)session.getAttribute("license_state");
		licenseState = licenseState.toUpperCase();
		String licenseNumber = (String)session.getAttribute("license_number");
		String dob = (String)session.getAttribute("dob");
		String lastName = (String)session.getAttribute("last_name");

		try{
			LocalDate date_of_birth = DatabaseUtilities.convertUSStringDateToLD(dob);
			session.setAttribute("license_state", licenseState);
			message = ListCitations(date_of_birth, licenseNumber,licenseState, lastName);
			if (message == ""){
				message = "No tickets were found. If you have entered your information correctly, please visit the following link for possible reasons: \n\n";
				message += clientURL+"/tickets/error/notFound";
				message += "\n\n To use this system again, reply with \"TICKET\".";
				message += "\n\n" + SMSSpamProtector.addError(session);
				nextTextStage = SMS_STAGE.WELCOME;
			}else{
				SMSSpamProtector.clearErrors(session);
				nextTextStage = SMS_STAGE.VIEW_CITATION;
			}
			
		}catch (DateTimeParseException e){
			//something went wrong here  this shouldn't happen since dob has already been parsed
			message = "Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.";
			nextTextStage = SMS_STAGE.WELCOME;
		}
		
		setNextStageInSession(session,nextTextStage);
		return message;
	}
	
	private String replyWithAdditionalViewingOptions(){
		String message = "";
		message += "\nReply with '1' to view another ticket";
		message += "\nReply with '2' for payment options";
		message += "\nReply with '3' to receive text message reminders about this court date";
		message += "\nReply with '4' to remove text message reminders about this court date";
		return message;
	}
	
	private String replyWithAdditionalViewingOptionsNoText(){
		String message = "";
		message += "\nReply with '1' to view another ticket";
		message += "\nReply with '2' for payment options";
		return message;
	}
	
	private String generateViewCitationsAgainMessage(HttpSession session, HttpServletRequest request, String menuChoice){
		String message = "";
		String citationNumber = (String)session.getAttribute("citationNumber");
		String courtDateTime = (String)session.getAttribute("courtDateTime");
		String phoneNumber = (String)session.getAttribute("phoneNumber");
		String dob = (String)session.getAttribute("dob");
		String zoneId = (String)session.getAttribute("zoneId");

		switch(menuChoice){
			case "1":
				message = generateReadStateMessage(session);
				break;
			case "2":
				message = "Visit ";
				message += clientURL+"/tickets";
				message += "/"+citationNumber;
				message += "/info";
				message += replyWithAdditionalViewingOptions();
				setNextStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
				break;
			case "3":
				if (smsAlertManager.add(citationNumber, LocalDateTime.parse(courtDateTime), zoneId, phoneNumber,DatabaseUtilities.convertUSStringDateToLD(dob))){
					//if a demo citation was created automatically send out an sms alert in 1 minute.
					if (citationNumber.startsWith("STLC")){
						Timer timer = new Timer();
						timer.schedule(new TimerTask(){

							@Override
							public void run() {
								smsNotifier.sendAlerts(citationNumber, phoneNumber);
								smsAlertManager.remove(citationNumber, phoneNumber, DatabaseUtilities.convertUSStringDateToLD(dob));
							}
							
						}, 1*60*1000);
					}
					
					message = "You will receive 3 text message reminders about your court date.  The first one will be sent two weeks prior to your court date.  The second will be send one week prior and the final one will be sent the day before your court date.";
					message += "\n\n  For help, respond HELP, to stop, respond STOP";
					message += "\n\n Responding with STOP will prevent you from receiving any reminders now and in the future as well as using any part of this SMS service.  If you'd like to cancel your reminder, you can cancel using the same text message menu you used to sign up.";
					message += "\n\n"+replyWithAdditionalViewingOptionsNoText();
					setNextStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
				}else{
					message = "Sorry, something went wrong in processing your request for text message reminders.";
					setNextStageInSession(session,SMS_STAGE.WELCOME);
				}
				break;
			case "4":
				if (smsAlertManager.remove(citationNumber, phoneNumber, DatabaseUtilities.convertUSStringDateToLD(dob))){
					message = "You have been removed from receiving text message reminders about this court date.  If there are other court dates you have signed up to receive text message reminders for and you would like to be removed from receiving updates about those dates, please look them up by your citation number and remove them too.";
					message += replyWithAdditionalViewingOptionsNoText();
					setNextStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
				}else{
					message = "Sorry, something went wrong in processing your request to be removed from text message reminders.";
					setNextStageInSession(session,SMS_STAGE.WELCOME);
				}
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
		List<Citation> citations;
		String message = "";
		SMS_STAGE nextTextStage;
		
		String dob = (String)session.getAttribute("dob");
		String license = (String)session.getAttribute("license_number");
		String licenseState = (String)session.getAttribute("license_state");
		String lastName = (String)session.getAttribute("last_name");
		criteria = new CitationSearchCriteria();
		try{
			criteria.dateOfBirth = DatabaseUtilities.convertUSStringDateToLD(dob);
			criteria.driversLicenseNumber = license;
			criteria.driversLicenseState = licenseState;
			criteria.lastName = lastName;
			citations = citationManager.findCitations(criteria);
			int citationNumberToView = Integer.parseInt(citationNumber) - 1;
			if (citationNumberToView >= 0 && citationNumberToView < citations.size()){
				Citation citationToView = citations.get(citationNumberToView);
				List<Violation> violations = violationManager.getViolationsByCitationNumber(citationToView.citation_number);
				Court court = courtManager.getCourtById(citationToView.court_id.getValue());
				CitationTextMessage citationTextMessage = new CitationTextMessage(citationToView,violations,court);
				message = citationTextMessage.toTextMessage();
				message += replyWithAdditionalViewingOptions();
				session.setAttribute("citationNumber", citationToView.citation_number);
				session.setAttribute("courtDateTime", citationToView.court_dateTime.toLocalDateTime().toString());
				session.setAttribute("zoneId", court.zone_id);
				nextTextStage = SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN;
				
			}else{
				message = "Invalid entry. Please enter only the number of the ticket you would like to view.";
				nextTextStage = SMS_STAGE.VIEW_CITATION;
			}

			
		}catch (DateTimeParseException e){
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
		MessagingResponse twimlResponse = null;
		if (!SMSSpamProtector.isLockedOut(session)){
			SMS_STAGE currentTextStage = getStageFromSession(session);
			String message = "";
			String content = twimlMessageRequest.getBody().trim();
			session.setAttribute("phoneNumber", twimlMessageRequest.getFrom());
			
			switch(currentTextStage){
				case WELCOME:
					message = generateWelcomeStageMessage(session);
					break;
				case READ_LASTNAME:
					message = generateReadLastNameMessage(session, content);
					break;
				case READ_DOB:
					message = generateReadDobMessage(session, content);
					break;
				case READ_LICENSE:
					message = generateReadLicenseMessage(session, content);
					break;
				case READ_STATE:
					message = generateReadStateMessage(session, content);
					break;
				case VIEW_CITATION:
					message = generateViewCitationMessage(session, content);
					break;
				case READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN:
					message = generateViewCitationsAgainMessage(session, request, content);
					break;
			}
		
			twimlResponse = createTwimlResponse(message);
		}
	    
	    return twimlResponse;
	}

}