package svc.data.citations.datasources.rejis.models;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

import svc.data.citations.datasources.rejis.RejisConstants;

public class RejisQueryObject {
	private String citationNumber;
	private String dob;
	private String municipalityCodes;
	private int pageNumber;
	private String driversLicenseNumber;
	private String driversLicenseState;
	private String lastName;
	
	private String queryType;

	
	public RejisQueryObject loadForTicket(int pageNumber, String citationNumber, LocalDate dob, List<String> municipalityCodes){
		this.pageNumber = pageNumber;
		this.citationNumber = citationNumber;
		this.dob = dob.toString();
		this.municipalityCodes = this.getMunicipalitiesString(municipalityCodes);
		
		this.queryType = RejisConstants.BY_TICKET;
		
		return this;
	}
	
	public RejisQueryObject loadForLicense(int pageNumber, String driversLicenseNumber, String driversLicenseState, String lastName, LocalDate dob, List<String> municipalityCodes){
		this.pageNumber = pageNumber;
		this.lastName = lastName;
		this.driversLicenseNumber = driversLicenseNumber;
		this.driversLicenseState = driversLicenseState;
		this.dob = dob.toString();
		this.municipalityCodes = this.getMunicipalitiesString(municipalityCodes);
		
		this.queryType = RejisConstants.BY_VEHICLE_LICENSE;
		
		return this;
	}
	
	public RejisQueryObject loadForName(int pageNumber, String lastName, LocalDate dob, List<String> municipalityCodes){
		this.pageNumber = pageNumber;
		this.lastName = lastName;
		this.dob = dob.toString();
		this.municipalityCodes = this.getMunicipalitiesString(municipalityCodes);
		
		this.queryType = RejisConstants.BY_NAME;
		
		return this;
	}
	
	public String getCitationNumber(){
		return this.citationNumber;
	}
	
	public String getDob(){
		return this.dob;
	};
	
	public String getMunicipalityCodes(){
		return this.municipalityCodes;
	};
	
	public int getPageNumber(){
		return this.pageNumber;
	};
	
	public String getDriversLicenseNumber(){
		return this.driversLicenseNumber;
	};
	
	public String getDriversLicenseState(){
		return this.driversLicenseState;
	};
	
	public String getLastName(){
		return this.lastName;
	};
	
	public String getQueryType(){
		return this.queryType;
	}
	
	
	private String getMunicipalitiesString(List<String> municipalityCodes){
		StringJoiner joiner  = new StringJoiner(",");
        for(String muni : municipalityCodes) { joiner.add(muni); }
        
        return joiner.toString();
	}
	
}
