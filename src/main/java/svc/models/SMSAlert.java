package svc.models;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class SMSAlert {
	public int id;
	public String defendantPhoneNumber;
	public String citationNumber;
	public LocalDate dob;
	public ZonedDateTime courtDate;
}
