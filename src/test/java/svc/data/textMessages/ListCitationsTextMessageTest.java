package svc.data.textMessages;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class ListCitationsTextMessageTest {
	
	@Test
	public void correctlyInitializesAndReturnsCorrectListOfCitations(){
		String dateString = "08/05/1965";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		Citation CITATION = new Citation();
		CITATION.citation_date = LocalDate.parse(dateString, formatter);
		
		ListCitationsTextMessage listCitationsTextMessage = new ListCitationsTextMessage(Arrays.asList(new Citation[]{CITATION}));
		String returnedString = listCitationsTextMessage.toTextMessage();
		
		String expectedString = "1 ticket was found\n1) ticket from: "+dateString;
		expectedString += "\nReply with the ticket number you want to view.";
		
		assertEquals(returnedString,expectedString);
		
		String dateString2 = "01/01/2000";
		
		Citation CITATION2 = new Citation();
		CITATION2.citation_date = LocalDate.parse(dateString2, formatter);
		
		listCitationsTextMessage = new ListCitationsTextMessage(Arrays.asList(new Citation[]{CITATION,CITATION2}));
		returnedString = listCitationsTextMessage.toTextMessage();
		
		expectedString = "2 tickets were found\n1) ticket from: "+dateString+"\n2) ticket from: "+dateString2;
		expectedString += "\nReply with the ticket number you want to view.";
		
		assertEquals(returnedString,expectedString);
		
		
	}
	
	
}
