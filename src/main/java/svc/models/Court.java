package svc.models;

import java.math.BigDecimal;
import java.util.Arrays;
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
	
	public List<Judge> judges;
	
	public final static List<String> getSQLNames(){
		//does not return judges, because 'judges' is added by sql join
		List<String> sqlNames = Arrays.asList("id","court_name","phone","website","extension","address","payment_system","city","state","zip_code","latitude","longitude");
		return sqlNames;
	}
}
