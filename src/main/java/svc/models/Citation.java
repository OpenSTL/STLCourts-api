package svc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import svc.annotations.Exclude;
import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.types.HashableEntity;

public class Citation {
	public int id;
	public String citation_number;
	public LocalDate citation_date;
	public String first_name;
	public String last_name;
	public LocalDate date_of_birth;
	public String defendant_address;
	public String defendant_city;
	public String defendant_state;
	public String drivers_license_number;
	public LocalDateTime court_dateTime;
	
	public List<Violation> violations;
	public HashableEntity<Court> court_id;
	public HashableEntity<Municipality> municipality_id;
	
	@Exclude
	public CITATION_DATASOURCE citation_datasource;
}
