package svc.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import svc.data.municipal.CourtDAO;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Violation;

@Component
public class SMSManager {
	
	public enum SMS_STAGE{
		WELCOME(0),
		READ_DOB(1),
		READ_LICENSE(2),
		VIEW_CITATION(3);
		
		private int numVal;
		
		SMS_STAGE(int numVal){
			this.numVal = numVal;
		}
		
		public int getNumVal(){
			return numVal;
		}
	}
	
	
	//a null String means validDOB, otherwise a message is returned to pass on to user
	public String validDOB(String dob){
		String errMsg = "";
		dob = dob.trim();
		if (!dob.matches("^\\d{2}([./-])\\d{2}\\1\\d{4}$")){
			errMsg = "Please re-enter your birthdate.  The format is: MM/DD/YYYY";
		}else{
			if (isValidDate(dob)){
				//Check that the DOB is > 18 years old
				DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");
				DateTime dobDT = formatter.parseDateTime(dob);
				DateTime now = new DateTime();
				Years age = Years.yearsBetween(dobDT, now);
				if (age.getYears() < 18){
					errMsg = "You must be at leat 18 years old to use www.yourSTLcourts.com";
				}
			}else{
				//dob is not a valid date
				errMsg = "The date you entered: '"+dob+"' was not a valid date.  Please re-enter your birthdate using MM/DD/YYYY";
			}
		}
		
		return errMsg;
	}
	
	
	private boolean isValidDate(String dateToCheck){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(dateToCheck.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	}
	
	public String convertDatabaseDateToUS(String databaseDate){
		SimpleDateFormat dataBaseFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
		try{
			return usFormat.format(dataBaseFormat.parse(databaseDate));
		}catch(ParseException e){
			return databaseDate; //error converting so just return the original date instead of crashing 
		}
		
	}
	
	public String convertDatabaseDateToUS(Date databaseDate){
		SimpleDateFormat usFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		return usFormat.format(databaseDate);
	}

}
