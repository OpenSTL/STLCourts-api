package svc.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.annotations.JsonAdapter;

import svc.gson.adapter.LocalDateJsonAdapter;
import svc.gson.adapter.LocalDateTimeJsonAdapter;

public class Citation {
	public int id;
	public String citation_number;
	@JsonAdapter(LocalDateJsonAdapter.class)
	public LocalDate citation_date;
	public String first_name;
	public String last_name;
	@JsonAdapter(LocalDateJsonAdapter.class)
	public LocalDate date_of_birth;
	public String defendant_address;
	public String defendant_city;
	public String defendant_state;
	public String drivers_license_number;
	@JsonAdapter(LocalDateTimeJsonAdapter.class)
	public LocalDateTime court_dateTime;
	public String court_location;
	public String court_address;
	
	public List<Violation> violations;
	public Long court_id;
}
