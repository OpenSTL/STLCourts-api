package svc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import svc.data.citations.datasources.CITATION_DATASOURCE;

public class SMSAlert {
	public int id;
	public String defendantPhoneNumber;
	public String citationNumber;
	public CITATION_DATASOURCE citationDataSource;
	public LocalDate dob;
	public LocalDateTime courtDate;
}
