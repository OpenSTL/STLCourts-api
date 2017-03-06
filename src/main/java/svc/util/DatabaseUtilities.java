package svc.util;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		if (databaseDate != null){
			return databaseDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
		}else{
			return "";
		}
	}
	
	public static String convertDatabaseDateToUS(LocalDateTime databaseDate){
		if (databaseDate != null){
			return databaseDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
		}else{
			return "";
		}
	}
	
	public static String convertDatabaseDateToUSTime(LocalDateTime databaseDate){
		if (databaseDate != null){
			return databaseDate.format(DateTimeFormatter.ofPattern("hh:mm a"));
		}else{
			return "";
		}
	}
	
	public static LocalDate getDatabaseLocalDate(Date databaseDate){
		if (databaseDate != null){
			return databaseDate.toLocalDate();
		}else{
			return null;
		}
	}
	
	public static LocalTime getDatabaseLocalTime(Time databaseDate){
		if (databaseDate != null){
			return databaseDate.toLocalTime();
		}else{
			return null;
		}
	}
}
