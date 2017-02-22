package svc.dto;

import java.util.Date;
import java.util.List;

public class CitationSearchCriteria {
	// Citation Number Criteria
	public String citationNumber;
	
	public Date citationDate;
	public Date courtDate;
	
	public Date dateOfBirth;

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
