package svc.data.citations.datasources.rejis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisPartialCitation {

	@JsonProperty
	public String AgcyId;

	@JsonProperty
	public String DefendantName;
	
	@JsonProperty
	public String lastName;

	@JsonProperty
	public Integer BirthYear;

	@JsonProperty
	public String CaseNum;
	
	@JsonProperty
	public String TktNum;

	@JsonProperty
	public String NextDktDate;
	
	@JsonProperty
	public String CourtName;
	
	@JsonProperty
	public String ChargeDesc;
	
	@JsonProperty
	public String CaseStatus;
	
	@JsonProperty
	public String CaseStatusDesc;
	
	@JsonProperty
	public String AgcyOri;
	
	@JsonProperty
	public double BalDue;
	
	@JsonProperty
	public boolean ShowIpaycourt;

	/*
	 *  What is this property do I need it
	 	@JsonProperty 
		public String? PbmStatus;
	*/
}
