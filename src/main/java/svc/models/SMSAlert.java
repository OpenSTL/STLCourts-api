package svc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import svc.types.HashableEntity;

public class SMSAlert {
	public int id;
	public String defendantPhoneNumber;
	public String citationNumber;
	public HashableEntity<Municipality> municipalityId;
	public LocalDate dob;
	public LocalDateTime courtDate;
}
