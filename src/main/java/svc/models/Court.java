package svc.models;

import java.math.BigDecimal;
import java.util.List;

public class Court {
	public Long id;
	public String name;
	public String phone;
	public String website;
	public String extension;
	public String address;
	public String paymentSystem;
	public String city;
	public String state;
	public String zip;
	public BigDecimal latitude;
	public BigDecimal longitude;
	public List<Judge> judges;
}
