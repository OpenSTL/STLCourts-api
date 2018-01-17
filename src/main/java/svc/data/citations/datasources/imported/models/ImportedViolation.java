package svc.data.citations.datasources.imported.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportedViolation {
	
	@JsonProperty("citation_number")
	public String citationNumber;

	@JsonProperty("violation_number")
	public String violationNumber;

	@JsonProperty("violation_description")
	public String violationDescription;

	@JsonProperty("warrant_status")
	public boolean warrantStatus;

	@JsonProperty("warrant_number")
	public String warrantNumber;

	@JsonProperty
	public String status;

	@JsonProperty("fine_amount")
	public BigDecimal fineAmount;

	@JsonProperty("can_pay_online")
	public Boolean canPayOnline;

}
