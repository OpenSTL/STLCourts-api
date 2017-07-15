package svc.data.citations.datasources.rejis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RejisFullCitation {

	@JsonProperty
	public String CaseStatus;
	
	@JsonProperty
	public String CaseStatusDesc;
	
	@JsonProperty
	public String CaseNum;
	
	@JsonProperty
	public String TktNum;
	
	@JsonProperty
	public String Ocn;
	    
	@JsonProperty
	public String CourtName;
	
	@JsonProperty
	public String BondSetAmt;
	
	@JsonProperty
	public String DeftName;
	
	@JsonProperty
	public String Dob;
	
	@JsonProperty
	public String DeftAddr;
	
	@JsonProperty
	public String ViolDttm;
	
	@JsonProperty
	public String ViolAddr;
	
	@JsonProperty
	public String IssuingAgcy;
	
	@JsonProperty
	public String ChrgCode;
	
	@JsonProperty
	public String ChrgDesc;
	
	@JsonProperty
	public String StateChrg;
	
	@JsonProperty
	public String Statute;
	    
	@JsonProperty
	public String DispDate;
	
	@JsonProperty
	public String Plea;
	
	@JsonProperty
	public String JudgeName;
	
	@JsonProperty
	public String DispDesc;
	
	@JsonProperty
	public String DefAttyName;
	
	@JsonProperty
	public String DefAttyPhone;
	
	@JsonProperty
	public String DefAttyAddr;
	
	@JsonProperty
	public String FilingDate;
	
	@JsonProperty
	public String OrigDktDate;
	
	@JsonProperty
	public String NextDktDate;
	
	@JsonProperty
	public String OrigDktCrtRm;
	
	@JsonProperty
	public String NextDktCrtRm;
	
	@JsonProperty
	public double AssdAmt;
	
	@JsonProperty
	public double BalDue;
	
	@JsonProperty
	public String PymtUrl;
	
	@JsonProperty
	public String CourtWebSite;
	
	@JsonProperty
	public String LastName;
	
	@JsonProperty
	public boolean isAgcyPlus;
	
	@JsonProperty
	public String AgcyId;
	
	@JsonProperty
	public String AgcyOri;
	
	@JsonProperty
	public String DispositionMoreInfo;
	
	@JsonProperty
	public String PymtUrlFullQueryString;
}