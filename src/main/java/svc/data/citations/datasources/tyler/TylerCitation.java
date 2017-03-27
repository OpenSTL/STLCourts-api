package svc.data.citations.datasources.tyler;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TylerCitation {
	public String citationNumber;
	public String firstname;
	public String lastName;
	public String driversLicenseNumber;
	public String dob;

	public List<TylerViolation> violations;
}
