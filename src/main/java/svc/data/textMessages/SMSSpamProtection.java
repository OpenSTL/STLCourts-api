package svc.data.textMessages;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

public class SMSSpamProtection {
	static final int MAX_ERROR_COUNT = 5;
	
	public static Boolean isLockedOut(HttpSession session){
		Boolean lockedOut = false;
		
		Integer errorCount = (Integer) session.getAttribute("errorCount");
		if (errorCount != null && errorCount >= MAX_ERROR_COUNT){
			lockedOut = true;
		}
		
		return lockedOut;
	}
	
	public static String addError(HttpSession session){
		String msg = "";
		Integer errorCount = (Integer) session.getAttribute("errorCount");
		if (errorCount == null){
			errorCount = 1;
		}else{
			errorCount++;
		}
		
		session.setAttribute("errorCount",errorCount);
		
		if (SMSSpamProtection.isLockedOut(session)){
			msg = "You have exceeded the maximum amount of invalid input errors.  This phone number is locked out for the next 20 minutes.";
	
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					session.setAttribute("errorCount",new Integer(0));
				}
				
			}, 1*60*1000);
		}
		
		return msg;
	}
	
	public static void clearErrors(HttpSession session){
		session.setAttribute("errorCount",new Integer(0));
	}
	
}
