package svc.models;

import java.math.BigDecimal;
import java.util.List;

import svc.types.HashableEntity;

public class Court {
	public HashableEntity<Court> id;
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
	public int citation_expires_after_days;
}
