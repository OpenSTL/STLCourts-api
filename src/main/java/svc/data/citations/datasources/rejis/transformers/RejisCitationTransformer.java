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
		if (rejisFullCitation == null) {
			return null;
		}
		
		Citation genericCitation = new Citation();
		genericCitation.citation_number = rejisFullCitation.TktNum;
		genericCitation.first_name = rejisFullCitation.DeftName.split(" ")[0];
		genericCitation.last_name = rejisFullCitation.LastName.substring(0, 1).toUpperCase() + rejisFullCitation.LastName.substring(1).toLowerCase();
		genericCitation.drivers_license_number = "";
		genericCitation.drivers_license_state = "";

		if (rejisFullCitation.Dob == null) {
			LogSystem.LogEvent("Received rejis citation with no DOB.");
		} else {
			genericCitation.date_of_birth = LocalDateTime.parse(rejisFullCitation.Dob).toLocalDate();
		}

		if (rejisFullCitation.ViolDttm == null) {
			LogSystem.LogEvent("Received rejis citation with no violation date.");
		} else {
			genericCitation.citation_date = LocalDateTime.parse(rejisFullCitation.ViolDttm).toLocalDate();
		}

		genericCitation.violations = violationTransformer.fromRejisFullCitation(rejisFullCitation, rejisPartialCitation);
		
		LocalDateTime NextDktDate = LocalDateTime.parse(rejisFullCitation.NextDktDate);
		LocalDateTime OrigDktDate = LocalDateTime.parse(rejisFullCitation.OrigDktDate);
		
		if (NextDktDate.isBefore(OrigDktDate)){
			genericCitation.court_dateTime = OrigDktDate;
		}else{
			genericCitation.court_dateTime = NextDktDate;
		}

		genericCitation.court_id = courtIdTransformer.lookupCourtId(CITATION_DATASOURCE.REJIS, rejisFullCitation.AgcyId);
		genericCitation.municipality_id = municipalityIdTransformer.lookupMunicipalityId(CITATION_DATASOURCE.REJIS,rejisFullCitation.AgcyId);
		
		String[] addressParts = rejisFullCitation.DeftAddr.split(" {2,}");
		genericCitation.defendant_address = addressParts[0].trim();
		String[] cityStateParts = addressParts[1].split(", ");
		genericCitation.defendant_city = cityStateParts[0];
		genericCitation.defendant_state = cityStateParts[1];

		return genericCitation;
	}
	
}
