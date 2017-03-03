package svc.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class DatabaseUtilitiesTest {
	
	@Test
	public void returnsTrueForValidUSDateString(){
		Boolean isValidDate = DatabaseUtilities.isStringValidUSDateString("02/14/1990");
		assertThat(isValidDate,is(true));
	}
	
	@Test
	public void returnsFalseForInvalidUSDateString(){
		Boolean isValidDate = DatabaseUtilities.isStringValidUSDateString("02/30/1990");
		assertThat(isValidDate,is(false));
		
		isValidDate = DatabaseUtilities.isStringValidUSDateString("aNonDate");
		assertThat(isValidDate,is(false));
		
		isValidDate = DatabaseUtilities.isStringValidUSDateString("13/400/2150");
		assertThat(isValidDate,is(false));
	}
	
	@Test
	public void correctlyConvertsDateObjToUSDateString(){
		String dateString = "08/05/1965";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
    
		String usDate = DatabaseUtilities.convertDatabaseDateToUS(localDate);
		assertEquals(dateString,usDate);
		
		String dateTimeString = dateString+" 15:30:22";
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter2);
		
		usDate = DatabaseUtilities.convertDatabaseDateToUS(localDateTime);
		assertEquals(dateString,usDate);	
	}
	
	@Test
	public void correctlyConvertsDateTimeObjToUSTime(){
		String dateTimeString = "08/05/1965 15:30:22";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
		
		String usTime = DatabaseUtilities.convertDatabaseDateToUSTime(localDateTime);
		assertEquals(usTime,"03:30 PM");
	}

}
