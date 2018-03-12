package svc.data.citations.datasources.rejis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisPartialCitation {

	@JsonProperty("AgcyId")
	public String agencyId;

	@JsonProperty("DefendantName")
	public String defendantName;
	
	@JsonProperty("lastName")
	public String lastName;

	@JsonProperty("BirthYear")
	public Integer birthYear;

	@JsonProperty("CaseNum")
	public String caseNumber;
	
	@JsonProperty("TktNum")
	public String ticketNumber;

	@JsonProperty("NextDktDate")
	public String nextCourtDate;
	
	@JsonProperty("CourtName")
	public String courtName;
	
	@JsonProperty("ChargeDesc")
	public String chargeDescription;
	
	@JsonProperty("CaseStatus")
	public String caseStatus;
	
	@JsonProperty("CaseStatusDesc")
	public String caseStatusDescription;
	
	@JsonProperty("AgcyOri")
	public String agencyOri;
	
	@JsonProperty("BalDue")
	public double balanceDue;
	
	@JsonProperty("ShowIpaycourt")
	public boolean showIpaycourt;

}
