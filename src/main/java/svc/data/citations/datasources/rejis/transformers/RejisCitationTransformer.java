package svc.data.citations.datasources.rejis.transformers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.logging.LogSystem;
import svc.models.Citation;

@Component
public class RejisCitationTransformer {

	@Autowired
	RejisViolationTransformer violationTransformer;

	@Autowired
	CourtIdTransformer courtIdTransformer;
	
	@Autowired
	MunicipalityIdTransformer municipalityIdTransformer;

	public Citation fromRejisFullCitation(RejisFullCitation rejisFullCitation, RejisPartialCitation rejisPartialCitation) {
		if (rejisFullCitation == null || rejisPartialCitation == null) {
			return null;
		}
		
		Citation genericCitation = new Citation();
		genericCitation.citation_number = rejisFullCitation.ticketNumber;
		try{
			genericCitation.first_name = rejisFullCitation.defendantName.split(" ")[0];
		}catch (Exception e){
			LogSystem.LogEvent("Unable to split defendant name: " + rejisFullCitation.defendantName);
			genericCitation.first_name = rejisFullCitation.defendantName;
		}
		genericCitation.last_name = rejisFullCitation.lastName.substring(0, 1).toUpperCase() + rejisFullCitation.lastName.substring(1).toLowerCase();
		genericCitation.drivers_license_number = "";
		genericCitation.drivers_license_state = "";

		try{
			genericCitation.date_of_birth = LocalDateTime.parse(rejisFullCitation.dob).toLocalDate();
		}catch(Exception e){
			LogSystem.LogEvent("Received rejis citation with no DOB.");
			return null;
		}
	
		try{
			genericCitation.citation_date = LocalDateTime.parse(rejisFullCitation.violationDateTime).toLocalDate();
		}catch(Exception e){
			LogSystem.LogEvent("Received rejis citation with no violation date.");
			return null;
		}

		genericCitation.violations = violationTransformer.fromRejisFullCitation(rejisFullCitation, rejisPartialCitation);
		
		try{
			LocalDateTime NextDktDate = LocalDateTime.parse(rejisFullCitation.nextCourtDate);
			LocalDateTime OrigDktDate = LocalDateTime.parse(rejisFullCitation.originalCourtDate);
			if (NextDktDate.isBefore(OrigDktDate)){
				genericCitation.court_dateTime = null;
			}else{
				genericCitation.court_dateTime = NextDktDate;
			}
		}catch(Exception e){
			LogSystem.LogEvent("Error parsing Rejis NextDktDate or OrigDktDate");
			return null;
		}

		genericCitation.court_id = courtIdTransformer.lookupCourtId(CITATION_DATASOURCE.REJIS, rejisFullCitation.agencyId);
		genericCitation.municipality_id = municipalityIdTransformer.lookupMunicipalityId(CITATION_DATASOURCE.REJIS,rejisFullCitation.agencyId);
		
		String[] addressParts = rejisFullCitation.defendantAddress.split(" {2,}");
		genericCitation.defendant_address = addressParts[0].trim();
		String[] cityStateParts = addressParts[1].split(", ");
		genericCitation.defendant_city = cityStateParts[0];
		genericCitation.defendant_state = cityStateParts[1];

		return genericCitation;
	}
	
}
