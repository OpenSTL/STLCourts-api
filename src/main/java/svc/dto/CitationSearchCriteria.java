package svc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class CitationSearchCriteria {
	// Citation Number Criteria
	public String citationNumber;
	
	public Date citationDate;
	public LocalDateTime courtDate;
	
	public LocalDate dateOfBirth;

	// Home Address Criteria (not used yet)
	public String defendantAddress;
	public String defendantCity;
	public String defendantState;
	
	// License Criteria
	public String driversLicenseNumber;
	public String driversLicenseState;

	// Name Criteria
	public String firstName;
	public String lastName;
	public List<Long> municipalities;
}
