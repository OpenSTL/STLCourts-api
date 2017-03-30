package svc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SMSAlert {
	public int id;
	public String defendantPhoneNumber;
	public String citationNumber;
	public int municipalityId;
	public LocalDate dob;
	public LocalDateTime courtDate;
}
