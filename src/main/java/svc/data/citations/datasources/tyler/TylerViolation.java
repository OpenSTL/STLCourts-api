package svc.data.citations.datasources.tyler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TylerViolation {
	public String violationNumber;
	public String violationDescription;
	public boolean warrantStatus;
	public String warrantNumber;
	public double fineAmount;
	public String status;
	public String courtDate;
	public String courtName;
}
