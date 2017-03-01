package svc.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.google.gson.annotations.JsonAdapter;

import svc.gson.adapter.LocalDateJsonAdapter;

public class Violation
{	
	public int id;
	public String citation_number;
	public String violation_number;
	public String violation_description;
	public boolean warrant_status;
	public String warrant_number;
	public VIOLATION_STATUS status;
	@JsonAdapter(LocalDateJsonAdapter.class)
	public LocalDate status_date;
	public BigDecimal fine_amount;
	public BigDecimal court_cost;
}
