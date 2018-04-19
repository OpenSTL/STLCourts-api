package svc.data.citations.datasources.importedITI.transformers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import svc.data.citations.datasources.importedITI.models.ImportedItiCitation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.data.transformer.CitationDateTimeTransformer;
import svc.logging.LogSystem;
import svc.models.Citation;
import svc.models.Court;
import svc.types.HashableEntity;

@Component
public class ImportedItiCitationTransformer {

	@Autowired
	ImportedItiViolationTransformer violationTransformer;

	@Autowired
	CourtIdTransformer courtIdTransformer;
	
	@Autowired
	MunicipalityIdTransformer municipalityIdTransformer;
	
	@Autowired
	CitationDateTimeTransformer citationDateTimeTransformer;

	public List<Citation> fromImportedItiCitations(List<ImportedItiCitation> importedItiCitations) {
		if (importedItiCitations != null) {
			return importedItiCitations.stream()
					.map(importedItiCitation -> fromImportedItiCitation(importedItiCitation))
					.collect(Collectors.toList());
		}
		return null;
	}


	public Citation fromImportedItiCitation(ImportedItiCitation importedCitation) {
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
			genericCitation.violations = violationTransformer.fromImportedCitation(importedCitation);

			genericCitation.court_id = new HashableEntity<Court>(Court.class, importedCitation.courtId); 
			//returns first municipality associated with the court
			genericCitation.municipality_id = municipalityIdTransformer.lookupMunicipalityIdFromCourtId(importedCitation.courtId);
			
			if (importedCitation.courtDateTime == null){
				LogSystem.LogEvent("received imported citation with no court date or time");
			} else {
				LocalDateTime localCourtDateTime = LocalDateTime.parse(importedCitation.courtDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
				genericCitation.court_dateTime = citationDateTimeTransformer.transformLocalDateTime(localCourtDateTime, genericCitation.court_id);
			}
		}

		genericCitation.defendant_address = importedCitation.defendantAddress;
		genericCitation.defendant_city = importedCitation.defendantCity;
		genericCitation.defendant_state = importedCitation.defendantState;

		return genericCitation;
	}
}
