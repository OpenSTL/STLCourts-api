package svc.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import svc.logging.LogSystem;

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
	
	public static String convertDatabaseDateToUS(ZonedDateTime databaseDate){
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
	
	public static String convertDatabaseDateToUSTime(ZonedDateTime databaseDate){
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
	
	public static LocalDate convertUSStringDateToLD(String dateString){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		return LocalDate.parse(dateString,formatter);	
	}
	
	public static LocalDate getCurrentDate(){
		return LocalDate.now(ZoneId.of("America/Chicago"));
	}
	
	public static LocalDateTime getCurrentDateTime(){
		return LocalDateTime.now(ZoneId.of("America/Chicago"));
	}
	
	public static Date convertLocalDateToDatabaseDate(LocalDate localDate){
		return Date.valueOf(localDate);
	}
	
	public static Date convertLocalDateTimeToDatabaseDate(LocalDateTime localDateTime){
		return new Date(localDateTime.atZone(ZoneId.of("America/Chicago")).toInstant().toEpochMilli());
	}
	
	public static String convertLocalDateTimeToDatabaseDateString(LocalDateTime localDateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}
	
	public static String convertZonedDateTimeToDatabaseDateString(ZonedDateTime zonedDateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return zonedDateTime.format(formatter);
	}
	
	public static String convertLocalDateToDatabaseDateString(LocalDate localDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return localDate.format(formatter);
	}
	
	public static LocalDateTime getFromDatabase(ResultSet rs, String fieldName){
		try {
			LocalDate date = DatabaseUtilities.getDatabaseLocalDate(rs.getDate(fieldName));
			LocalTime time = DatabaseUtilities.getDatabaseLocalTime(rs.getTime(fieldName));
			if (date==null || time==null){
				return null;
			}else{
				return LocalDateTime.of(date, time);
			}
		} catch (SQLException e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
	public static ZonedDateTime getFromDatabase(ResultSet rs, String fieldName, String zoneId){
		try {
			LocalDate date = DatabaseUtilities.getDatabaseLocalDate(rs.getDate(fieldName));
			LocalTime time = DatabaseUtilities.getDatabaseLocalTime(rs.getTime(fieldName));
			if (date==null || time==null){
				return null;
			}else{
				if (zoneId == null || zoneId == "") {
					zoneId = "America/Chicago";
				}
				return ZonedDateTime.of(LocalDateTime.of(date, time), ZoneId.of(zoneId));
			}
		} catch (SQLException e) {
			LogSystem.LogDBException(e);
			return null;
		}
	}
	
}
