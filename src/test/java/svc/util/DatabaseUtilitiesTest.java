package svc.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
	public void correctlyConvertsDateObjToUSDateString() throws ParseException{
        String dateString = "08/05/1965";
        DateFormat  format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = format.parse(dateString);

		String usDate = DatabaseUtilities.convertDatabaseDateToUS(date);
		assertEquals(dateString,usDate);
		
	}

}
