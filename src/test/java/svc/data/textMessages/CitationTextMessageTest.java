package svc.data.textMessages;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import svc.models.Citation;
import svc.models.Court;
import svc.models.VIOLATION_STATUS;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class CitationTextMessageTest {
	
	@Test
	public void correctlyInitializesAndReturnsCorrectCitationText() throws ParseException{
		String ticketDateString = "08/05/2015";
		String courtDateString = "09/10/2016";
		String violationStatusDateString = "11/01/2002";
		DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        
		
		Citation CITATION = new Citation();
		CITATION.id = 5;
		CITATION.citation_date = format.parse(ticketDateString);
		CITATION.court_date = format.parse(courtDateString);
		CITATION.citation_number = "123";
		
		Court COURT = new Court();
		COURT.address = "AnyStreet";
		COURT.city = "AnyCity";
		COURT.state = "State";
		COURT.zip_code = "ABC";
		
		Violation VIOLATION = new Violation();
		VIOLATION.id = 4;
		VIOLATION.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		VIOLATION.violation_number = "8910";
		VIOLATION.violation_description = "hello";
		VIOLATION.status_date = format.parse(violationStatusDateString);
		VIOLATION.fine_amount = new BigDecimal("2.00");
		VIOLATION.court_cost = new BigDecimal("3.00");
		List<Violation> VIOLATIONS = Arrays.asList(new Violation[]{VIOLATION});
		
		String expectedViolationMessage = "\nViolation #: 8910\nViolation: hello\nStatus (as of "+violationStatusDateString+"): "+VIOLATION.status.toString();
		expectedViolationMessage += "\nFine Amount: $2.00\nCourt Costs: $3.00";
		
		String expectedCitationMessage = "Ticket Date: " + ticketDateString+"\nCourt Date: "+courtDateString+"\nTicket #: 123";
		expectedCitationMessage += "\nCourt Address: "+COURT.address+" "+COURT.city+", "+COURT.state+" "+COURT.zip_code;
		expectedCitationMessage += expectedViolationMessage;
		
		CitationTextMessage citationTextMessageObj = new CitationTextMessage(CITATION, VIOLATIONS, COURT);
		String citationTextMessage = citationTextMessageObj.toTextMessage();
		
		assertEquals(citationTextMessage,expectedCitationMessage);
		
	}

}