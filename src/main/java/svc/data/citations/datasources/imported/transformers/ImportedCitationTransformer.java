package svc.data.citations.datasources.imported.transformers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.imported.models.ImportedCitation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class ImportedCitationTransformer {

	@Autowired
	ImportedViolationTransformer violationTransformer;

	@Autowired
	CourtIdTransformer courtIdTransformer;
	
	@Autowired
	MunicipalityIdTransformer municipalityIdTransformer;

	public List<Citation> fromImportedCitations(List<ImportedCitation> importedCitations) {
		if (importedCitations != null) {
			return importedCitations.stream()
					.map(importedCitation -> fromImportedCitation(importedCitation))
					.collect(Collectors.toList());
		}
		return null;
	}


	public Citation fromImportedCitation(ImportedCitation importedCitation) {
		if (importedCitation == null) {
			return null;
		}

		Citation genericCitation = new Citation();
		genericCitation.citation_number = importedCitation.citationNumber;
		genericCitation.first_name = importedCitation.firstName;
		genericCitation.last_name = importedCitation.lastName;
		genericCitation.drivers_license_number = importedCitation.driversLicenseNumber;
		genericCitation.drivers_license_state = importedCitation.driversLicenseState;

		if (importedCitation.dateOfBirth == null) {
			LogSystem.LogEvent("Received imported citation with no DOB.");
		} else {
			genericCitation.date_of_birth = LocalDate.parse(importedCitation.dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE);
		}

		if (importedCitation.citationDate == null) {
			LogSystem.LogEvent("Received imported citation with no violation date.");
		} else {
			genericCitation.citation_date = LocalDate.parse(importedCitation.citationDate, DateTimeFormatter.ISO_LOCAL_DATE);
		}

		if (importedCitation.violations == null) {
			LogSystem.LogEvent("No violations received with imported citation. Skipping fields that require them.");
		} else {
			if (importedCitation.courtDateTime == null){
				LogSystem.LogEvent("received imported citation with no court date or time");
			} else {
				genericCitation.court_dateTime = LocalDateTime.parse(importedCitation.courtDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			}
			
			genericCitation.violations = violationTransformer.fromImportedCitation(importedCitation);

			genericCitation.court_id = new HashableEntity<Court>(Court.class, importedCitation.courtId); 
			//returns first municipality associated with the court
			genericCitation.municipality_id = municipalityIdTransformer.lookupMunicipalityIdFromCourtId(importedCitation.courtId);
		}

		genericCitation.defendant_address = importedCitation.defendantAddress;
		genericCitation.defendant_city = importedCitation.defendantCity;
		genericCitation.defendant_state = importedCitation.defendantState;

		return genericCitation;
	}
}
