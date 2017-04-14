package svc.models;

import java.math.BigDecimal;

public class Violation
{	
	public int id;
	public String citation_number;
	public String violation_number;
	public String violation_description;
	public boolean warrant_status;
	public String warrant_number;
	public VIOLATION_STATUS status;
	public BigDecimal fine_amount;
	public BigDecimal court_cost;
	public Boolean can_pay_online;
}
