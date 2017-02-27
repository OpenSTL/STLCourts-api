package svc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseUtilities {
	public static boolean isStringValidUSDateString(String dateToCheck){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(dateToCheck.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	}
	
	public static String convertDatabaseDateToUS(LocalDate databaseDate){
		return databaseDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}
	
	public static String convertDatabaseDateToUS(LocalDateTime databaseDate){
		return databaseDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
	}
	
	public static String convertDatabaseDateToUSTime(LocalDateTime databaseDate){
		return databaseDate.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}
}
