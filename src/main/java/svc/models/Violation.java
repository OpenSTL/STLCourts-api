package svc.models;

import java.math.BigDecimal;
import java.util.Date;

public class Violation
{	
	public int id;
	public String citation_number;
	public String violation_number;
	public String violation_description;
	public boolean warrant_status;
	public String warrant_number;
	public VIOLATION_STATUS status;
	public Date status_date;
	public BigDecimal fine_amount;
	public BigDecimal court_cost;
}
