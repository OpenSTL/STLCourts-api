package svc.models;

import java.math.BigDecimal;
import java.util.List;

public class Court 
{
	public int id;
	public String court_name;
	public String phone;
	public String website;
	public String extension;
	public String address;
	public String payment_system;
	public String city;
	public String state;
	public String zip_code;
	public BigDecimal latitude;
	public BigDecimal longitude;
	
	public List<MunicipalityJudge> municipality_judges;
}
