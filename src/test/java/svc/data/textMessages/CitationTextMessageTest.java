package svc.data.textMessages;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import svc.models.Citation;
import svc.models.Court;
import svc.models.VIOLATION_STATUS;
import svc.models.Violation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CitationTextMessageTest {
	
	@Test
	public void correctlyInitializesAndReturnsCorrectCitationText(){
		String ticketDateString = "08/05/2015";
		String courtDateString = "09/10/2016 14:33";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        
		
		Citation CITATION = new Citation();
		CITATION.id = 5;
		CITATION.citation_date = LocalDate.parse(ticketDateString, formatter);
		CITATION.court_dateTime = LocalDateTime.parse(courtDateString, formatter2);
		CITATION.citation_number = "123";
		
		Court COURT = new Court();
		COURT.address = "AnyStreet";
		COURT.city = "AnyCity";
		COURT.state = "State";
		COURT.zip = "ABC";
		
		Violation VIOLATION = new Violation();
		VIOLATION.id = 4;
		VIOLATION.status = VIOLATION_STATUS.CONT_FOR_PAYMENT;
		VIOLATION.violation_number = "8910";
		VIOLATION.violation_description = "hello";
		VIOLATION.fine_amount = new BigDecimal("2.00");
		VIOLATION.court_cost = new BigDecimal("3.00");
		List<Violation> VIOLATIONS = Lists.newArrayList(VIOLATION);
	
		String expectedViolationMessage = "\nViolation #: 8910\nViolation: hello\nStatus: "+VIOLATION.status.toString();
		expectedViolationMessage += "\nFine Amount: $2.00\nCourt Costs: $3.00";
		
		String expectedCitationMessage = "Ticket Date: " + ticketDateString+"\nCourt Date: 09/10/2016\nCourt Time: 02:33 PM\nTicket #: 123";
		expectedCitationMessage += "\nCourt Address: "+COURT.address+" "+COURT.city+", "+COURT.state+" "+COURT.zip;
		expectedCitationMessage += expectedViolationMessage;
		
		CitationTextMessage citationTextMessageObj = new CitationTextMessage(CITATION, VIOLATIONS, COURT);
		String citationTextMessage = citationTextMessageObj.toTextMessage();
		
		assertEquals(citationTextMessage,expectedCitationMessage);
		
	}

}
