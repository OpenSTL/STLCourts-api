package svc.managers;

import com.google.common.collect.Lists;
import com.twilio.twiml.Body;
import com.twilio.twiml.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.dto.CitationSearchCriteria;
import svc.models.*;
import svc.types.HashableEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SMSManagerTest {
	@InjectMocks
	SMSManager manager;
	
	@Mock
	CourtManager courtManagerMock;
	@Mock
	ViolationManager violationManagerMock;
	@Mock
	CitationManager citationManagerMock;
	@Mock
	HttpServletRequest requestMock;
	
	MockHttpSession session;
	
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
	
	private MessagingResponse createTwimlResponse(String msg){
		Message sms = new Message.Builder().body(new Body(msg)).build();
	    MessagingResponse twimlResponse = new MessagingResponse.Builder().message(sms).build();
	    return twimlResponse;
	}
	
	
	private void setStageInSession(HttpSession session, SMS_STAGE textStage){
		session.setAttribute("stage", new Integer(textStage.getNumVal()));
	}
	
	 @Before
	    public void setup() {
		 	session = new MockHttpSession();
	    }
	
	@Test
	public void welcomeMessageGetsGenerated() throws TwiMLException{
		setStageInSession(session,SMS_STAGE.WELCOME);
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("");
		String message = "Welcome to www.yourSTLcourts.com.  Please enter your birthdate using MM/DD/YYYY";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest,requestMock, session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
	
	@Test
	public void dobReadMessageGetsGenerated() throws TwiMLException{
		setStageInSession(session,SMS_STAGE.READ_DOB);
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("06/01/1963");
		String message = "Thank you.  Now please enter your driver\'s license number.";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest, requestMock,session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
	
	@Test
	public void licenseReadMessageGetsGenerated() throws TwiMLException, ParseException{
		setStageInSession(session,SMS_STAGE.READ_LICENSE);
		session.setAttribute("dob", "06/01/1963");
		Citation citation = new Citation();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		citation.citation_date = LocalDate.parse("02/03/1990", formatter);
		List<Citation> citations = new ArrayList<Citation>();
		citations.add(citation);
		
		when(citationManagerMock.findCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("F917801962");
		String message = "1 ticket was found\n1) ticket from: 02/03/1990\nReply with the ticket number you want to view.";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest,requestMock, session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
	
	@Test
	public void citationDetailMessageGetsGenerated() throws TwiMLException, ParseException{
		setStageInSession(session,SMS_STAGE.VIEW_CITATION);
		session.setAttribute("dob", "06/01/1963");
		session.setAttribute("license_number", "F917801962");
		
		Citation citation = new Citation();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		citation.citation_date = LocalDate.parse("02/03/1990", formatter);
		citation.citation_number = "a1234";
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		citation.court_dateTime = LocalDateTime.parse("11/20/2015 04:22", formatter2);
		citation.court_id = new HashableEntity<Court>(Court.class,1L);
		CitationDataSourceWrapper citationDataSourceWrapper = new CitationDataSourceWrapper();
		citationDataSourceWrapper.citationDataSource = CITATION_DATASOURCE.MOCK;
		citationDataSourceWrapper.citation = citation;
		List<CitationDataSourceWrapper> citationDataSourceWrappers = Lists.newArrayList(citationDataSourceWrapper);
		
		Court court = new Court();
		court.address = "1 Anystreet";
		court.city = "myCity";
		court.state = "myState";
		court.zip = "myZip";
		
		Violation violation = new Violation();
		violation.violation_number = "Y246";
		violation.violation_description = "myDescription";
		violation.status_date = LocalDateTime.parse("12/01/2015 09:14", formatter2);
		violation.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		violation.fine_amount = new BigDecimal(200.54);
		violation.court_cost = new BigDecimal(22.34);
		List<Violation> violations = new ArrayList<Violation>();
		violations.add(violation);
		
		
		when(citationManagerMock.findCitationsInDataSourceWrapper((CitationSearchCriteria)notNull())).thenReturn(citationDataSourceWrappers);
		when(courtManagerMock.getCourtById(citationDataSourceWrappers.get(0).citation.court_id.getValue())).thenReturn(court);
		when(violationManagerMock.getViolationsByCitationNumber(anyString())).thenReturn(violations);
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("1");
		String message = "Ticket Date: 02/03/1990\nCourt Date: 11/20/2015\nCourt Time: 04:22 AM\nTicket #: "+citation.citation_number;
		message += "\nCourt Address: "+court.address+" "+court.city+", "+court.state+" "+court.zip;
		message += "\nViolation #: "+violation.violation_number+"\nViolation: "+violation.violation_description;
		message += "\nStatus (as of 12/01/2015): "+violation.status.toString();
		message += "\nFine Amount: $"+violation.fine_amount;
		message += "\nCourt Costs: $"+violation.court_cost;
		message += "\nReply with '1' to view another ticket";
		message += "\nReply with '2' for payment options";
		message += "\nReply with '3' to receive text message reminders about this court date";
		message += "\nReply with '4' to remove text message reminders about this court date";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest,requestMock, session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
	
	@Test
	public void licenseReadMessageGetsGeneratedAgain() throws TwiMLException, ParseException{
		setStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
		session.setAttribute("dob", "06/01/1963");
		session.setAttribute("license_number", "F917801962");
		Citation citation = new Citation();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		citation.citation_date = LocalDate.parse("02/03/1990", formatter);
		List<Citation> citations = new ArrayList<Citation>();
		citations.add(citation);
		
		when(citationManagerMock.findCitations((CitationSearchCriteria)notNull())).thenReturn(citations);
		
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("1");
		String message = "1 ticket was found\n1) ticket from: 02/03/1990\nReply with the ticket number you want to view.";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest,requestMock, session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
	
	@Test
	public void checkPaymentURL() throws TwiMLException{
		setStageInSession(session,SMS_STAGE.READ_MENU_CHOICE_VIEW_CITATIONS_AGAIN);
		session.setAttribute("citationNumber", "ABC");
		
		TwimlMessageRequest twimlMessageRequest = new TwimlMessageRequest();
		twimlMessageRequest.setBody("2");
		String message = "Visit null/citations/ABC";
		message += "\nReply with '1' to view another ticket";
		message += "\nReply with '2' for payment options";
		message += "\nReply with '3' to receive text message reminders about this court date";
		message += "\nReply with '4' to remove text message reminders about this court date";
		MessagingResponse twimlResponse = manager.getTwimlResponse(twimlMessageRequest,requestMock, session);
		assertEquals(createTwimlResponse(message).toXml(),twimlResponse.toXml());
	}
}
