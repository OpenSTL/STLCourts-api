package svc.data.textMessages;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class ListCitationsTextMessageTest {
	
	@Test
	public void correctlyInitializesAndReturnsCorrectListOfCitations() throws ParseException{
		String dateString = "08/05/1965";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		
		Citation CITATION = new Citation();
		CITATION.citation_date = format.parse(dateString);;
		
		ListCitationsTextMessage listCitationsTextMessage = new ListCitationsTextMessage(Arrays.asList(new Citation[]{CITATION}));
		String returnedString = listCitationsTextMessage.toTextMessage();
		
		String expectedString = "1 ticket was found\n1) ticket from: "+dateString;
		expectedString += "\nReply with the ticket number you want to view.";
		
		assertEquals(returnedString,expectedString);
		
		String dateString2 = "01/01/2000";
		
		Citation CITATION2 = new Citation();
		CITATION2.citation_date = format.parse(dateString2);;
		
		listCitationsTextMessage = new ListCitationsTextMessage(Arrays.asList(new Citation[]{CITATION,CITATION2}));
		returnedString = listCitationsTextMessage.toTextMessage();
		
		expectedString = "2 tickets were found\n1) ticket from: "+dateString+"\n2) ticket from: "+dateString2;
		expectedString += "\nReply with the ticket number you want to view.";
		
		assertEquals(returnedString,expectedString);
		
		
	}
	
	
}
