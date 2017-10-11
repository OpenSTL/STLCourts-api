package svc.data.citations.datasources.rejis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisFullCitation {

	@JsonProperty("CaseStatus")
	public String caseStatus;
	
	@JsonProperty("CaseStatusDesc")
	public String caseStatusDesc;
	
	@JsonProperty("CaseNum")
	public String caseNumber;
	
	@JsonProperty("TktNum")
	public String ticketNumber;
	
	@JsonProperty("Ocn")
	public String ocn;
	    
	@JsonProperty("CourtName")
	public String courtName;
	
	@JsonProperty("BondSetAmt")
	public String bondSetAmt;
	
	@JsonProperty("DeftName")
	public String defendantName;
	
	@JsonProperty("Dob")
	public String dob;
	
	@JsonProperty("DeftAddr")
	public String defendantAddress;
	
	@JsonProperty("ViolDttm")
	public String violationDateTime;
	
	@JsonProperty("ViolAddr")
	public String violationAddress;
	
	@JsonProperty("IssuingAgcy")
	public String issuingAgency;
	
	@JsonProperty("ChrgCode")
	public String chargeCode;
	
	@JsonProperty("ChrgDesc")
	public String chargeDescription;
	
	@JsonProperty("StateChrg")
	public String stateCharge;
	
	@JsonProperty("Statute")
	public String statute;
	    
	@JsonProperty("DispDate")
	public String displayDate;
	
	@JsonProperty("Plea")
	public String plea;
	
	@JsonProperty("JudgeName")
	public String judgeName;
	
	@JsonProperty("DispDesc")
	public String displayDescription;
	
	@JsonProperty("DefAttyName")
	public String defenseAttorneyName;
	
	@JsonProperty("DefAttyPhone")
	public String defenseAttorneyPhone;
	
	@JsonProperty("DefAttyAddr")
	public String defenseAttorneyAddress;
	
	@JsonProperty("FilingDate")
	public String filingDate;
	
	@JsonProperty("OrigDktDate")
	public String originalCourtDate;
	
	@JsonProperty("NextDktDate")
	public String nextCourtDate;
	
	@JsonProperty("OrigDktCrtRm")
	public String originalCourtRoom;
	
	@JsonProperty("NextDktCrtRm")
	public String nextCourtRoom;
	
	@JsonProperty("assdAmt")
	public double assesedAmount;
	
	@JsonProperty("BalDue")
	public double balanceDue;
	
	@JsonProperty("PymtUrl")
	public String paymentUrl;
	
	@JsonProperty("CourtWebSite")
	public String courtWebSite;
	
	@JsonProperty("LastName")
	public String lastName;
	
	@JsonProperty("isAgcyPlus")
	public boolean isAgcyPlus;
	
	@JsonProperty("AgcyId")
	public String agencyId;
	
	@JsonProperty("AccyOri")
	public String agencyOri;
	
	@JsonProperty("DispositionMoreInfo")
	public String dispositionMoreInfo;
	
	@JsonProperty("PymtUrlFullQueryString")
	public String paymentUrlFullQueryString;
}