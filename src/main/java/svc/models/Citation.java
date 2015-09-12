package svc.models;

import java.util.Date;
import java.util.List;

public class Citation 
{
	public int id;
	public String citation_number;
	public Date citation_date;
	public String first_name;
	public String last_name;
	public Date date_of_birth;
	public String defendant_address;
	public String defendant_city;
	public String defendant_state;
	public String drivers_license_number;
	public Date court_date;
	public String court_location;
	public String court_address;
	
	public List<Violation> violations;
	public int court_id;
}
