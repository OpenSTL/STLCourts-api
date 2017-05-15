package svc.data.citations.datasources.tyler.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TylerCitation {

	@JsonProperty
	public String citationNumber;

	@JsonProperty
	public String firstName;

	@JsonProperty
	public String lastName;

	@JsonProperty
	public String driversLicenseNumber;
	
	@JsonProperty
	public String driversLicenseState;

	@JsonProperty
	public String dob;
	
	@JsonProperty
	public String violationDate;

	@JsonProperty
	public List<TylerViolation> violations;
}
