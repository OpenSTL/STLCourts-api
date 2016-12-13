package svc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static String convertDatabaseDateToUS(Date databaseDate){
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		return usFormat.format(databaseDate);
	}
}
