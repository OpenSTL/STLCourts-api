package svc.controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.runners.MockitoJUnitRunner;

import com.jayway.jsonpath.Criteria;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;

import static org.hamcrest.CoreMatchers.*;

import svc.dto.CitationSearchCriteria;
import svc.dto.CourtsDTO;
import svc.managers.CitationManager;
import svc.managers.CourtManager;
import svc.managers.SMSManager;
import svc.models.Court;
import svc.models.*;

@RunWith(MockitoJUnitRunner.class)
public class SMSControllerTest {

	@InjectMocks
	SMSController controller;
	
	@Mock
	SMSManager smsManagerMock;
	@Mock
	CitationManager citationManagerMock;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	
	
	@Before
	public void init() throws IOException{		
		Message sms = new Message.Builder().body(new Body("messageString")).build();
		MessagingResponse twimlResponse = new MessagingResponse.Builder().message(sms).build();
		when(smsManagerMock.getTwimlResponse(anyString())).thenReturn(twimlResponse);
	
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);
	}
	
	@Test 
	public void returnsCorrectWelcomeMessage() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		controller.GetMessage(twimlMessageRequest, response, session);
		verify(smsManagerMock).getTwimlResponse("Welcome to www.yourSTLcourts.com.  Please enter your birthdate using MM/DD/YYYY");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.READ_DOB.ordinal());
	}
	
	@Test 
	public void returnsCorrectMessageAfterReadValidBirthdate() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("06/15/1981");
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.READ_DOB.ordinal());
		when(smsManagerMock.validDOB(anyString())).thenReturn("");
		controller.GetMessage(twimlMessageRequest, response, session);
		verify(smsManagerMock).getTwimlResponse("Thank you.  Now please enter your driver\'s license number.");
		verify(session).setAttribute("dob", "06/15/1981");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.READ_LICENSE.ordinal());
	}
	
	@Test 
	public void returnsCorrectMessageAfterReadInvalidBirthdate() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("");
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.READ_DOB.ordinal());
		when(smsManagerMock.validDOB(anyString())).thenReturn("Invalid DOB String");
		controller.GetMessage(twimlMessageRequest, response, session);
		verify(smsManagerMock).getTwimlResponse("Invalid DOB String");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.READ_DOB.ordinal());
	}
	
	@Test 
	public void returnsCorrectMessageAfterReadValidLicense() throws IOException, ParseException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("H234567");
		
		List<Citation> citations = new ArrayList<Citation>();
		Citation citation  = new Citation();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		citation.citation_date = format.parse("2015-06-15");
		citations.add(citation);
		
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.READ_LICENSE.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		when(smsManagerMock.validDOB(anyString())).thenReturn("");
		when(citationManagerMock.FindCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		when(smsManagerMock.convertDatabaseDateToUS((Date)notNull())).thenReturn("06/15/2015");
		
		controller.GetMessage(twimlMessageRequest, response, session);
		verify(session).setAttribute("license_number", "H234567");
		verify(smsManagerMock).getTwimlResponse("1 ticket was found\n1) ticket from: 06/15/2015\nReply with the ticket number you want to view.");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
	}
	
	@Test 
	public void returnsCorrectMessageAfterReadValidLicenseNoCitationsFound() throws IOException, ParseException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("H234567");
		
		List<Citation> citations = new ArrayList<Citation>();
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.READ_LICENSE.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		when(citationManagerMock.FindCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		
		controller.GetMessage(twimlMessageRequest, response, session);
		verify(session).setAttribute("license_number", "H234567");
		verify(smsManagerMock).getTwimlResponse("No tickets were found.");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.WELCOME.ordinal());
	}
	
	@Test 
	public void returnsCorrectMessageAfterBadBirthDateInReadLicense() throws IOException, ParseException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.READ_LICENSE.ordinal());
		when(session.getAttribute("dob")).thenReturn("badDate");
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.WELCOME.ordinal());
	}
	
	@Test
	public void returnsCorrectMessageWhenCitationToViewIsLessThanOne() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("0");
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Invalid entry. Please enter only the number of the ticket you would like to view.");
	}
	
	@Test
	public void returnsCorrectMessageWhenCitationToViewIsGreaterThanNumberOfCitations() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("3");
		List<Citation> citations = new ArrayList<Citation>();
		Citation citation  = new Citation();
		citations.add(citation);
		citations.add(citation);
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		when(citationManagerMock.FindCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Invalid entry. Please enter only the number of the ticket you would like to view.");
	}
	
	@Test
	public void returnsCorrectMessageWhenBadCitationNumberGiven() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("A");
		List<Citation> citations = new ArrayList<Citation>();
		Citation citation  = new Citation();
		citations.add(citation);
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		when(citationManagerMock.FindCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Invalid entry. Please enter only the number of the ticket you would like to view.");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
	}
	
	@Test
	public void returnsCorrectMessageWhenBirthdateStoredIsBadInViewCitations() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
		when(session.getAttribute("dob")).thenReturn("badDate");
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Sorry, something went wrong.  Please use the website www.yourSTLcourts.com.");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.WELCOME.ordinal());
	}
	
	@Test
	public void returnsCorrectMessageForCitations() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("1");
		List<Citation> citations = new ArrayList<Citation>();
		Citation citation  = new Citation();
		citations.add(citation);
		
		
		when(session.getAttribute("stage")).thenReturn(SMSManager.SMS_STAGE.VIEW_CITATION.ordinal());
		when(session.getAttribute("dob")).thenReturn("06/15/1981");
		when(citationManagerMock.FindCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		when(citationManagerMock.getCitationSMS((Citation)notNull())).thenReturn("CitationMessage");
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("CitationMessage");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.WELCOME.ordinal());
		
	}
	
	@Test
	public void correctlyTriggersWelcomeMessageForBadStage() throws IOException{
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		
		when(session.getAttribute("stage")).thenReturn(-1);
		
		controller.GetMessage(twimlMessageRequest, response, session);
		
		verify(smsManagerMock).getTwimlResponse("Welcome to www.yourSTLcourts.com.  Please enter your birthdate using MM/DD/YYYY");
		verify(session).setAttribute("stage", SMSManager.SMS_STAGE.READ_DOB.ordinal());
	}
	
	
	
	
}
