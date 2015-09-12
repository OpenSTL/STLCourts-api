package svc.dto;

import java.util.Date;
import java.util.List;

public class CitationSearchCriteria 
{
	// Citation Number Criteria
	public String citation_number;
	
	public Date citation_date;
	public Date court_date;
	
	public Date date_of_birth;

	// Home Address Criteria (not used yet)
	public String defendant_address;
	public String defendant_city;
	public String defendant_state;
	
	// License Criteria
	public String drivers_license_number;
	public String drivers_license_state;

	// Name Criteria
	public String first_name;
	public String last_name;
	public List<String> municipalities;
}
