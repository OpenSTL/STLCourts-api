package svc.data.citations.datasources.imported.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportedCitation {
	
	@JsonProperty("citation_number")
	public String citationNumber;
	
	@JsonProperty("citation_date")
	public String citationDate;
	
	@JsonProperty("case_status")
	public String caseStatus;

	@JsonProperty("first_name")
	public String firstName;

	@JsonProperty("last_name")
	public String lastName;

	@JsonProperty("date_of_birth")
	public String dateOfBirth;

	@JsonProperty("defendant_address")
	public String defendantAddress;

	@JsonProperty("defendant_city")
	public String defendantCity;

	@JsonProperty("defendant_state")
	public String defendantState;

	@JsonProperty("drivers_license_number")
	public String driversLicenseNumber;

	@JsonProperty("drivers_license_state")
	public String driversLicenseState;

	@JsonProperty("court_dateTime")
	public String courtDateTime;

	@JsonProperty("court_id")
	public Long courtId;

	@JsonProperty
	public List<ImportedViolation> violations;
}
