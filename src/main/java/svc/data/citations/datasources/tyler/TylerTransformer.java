package svc.data.citations.datasources.tyler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import svc.models.Citation;
import svc.models.Violation;

@Component
public class TylerTransformer {

	private DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");

	public List<Citation> citationsFromTylerCitations(List<TylerCitation> tylerCitations) {
		if (tylerCitations != null) {
			return tylerCitations.stream()
					.map(tylerCitation -> citationFromTylerCitation(tylerCitation))
					.collect(Collectors.toList());
		}
		return null;
	}

	private LocalDateTime parseViolationCourtDate(String violationCourtDateString) {
		try {
			return LocalDateTime.parse(violationCourtDateString);
		} catch (DateTimeParseException ex) {
		}
		return null;
	}

	public Citation citationFromTylerCitation(TylerCitation tylerCitation) {

		Citation output = new Citation();
		output.citation_number = tylerCitation.citationNumber;
		output.first_name = tylerCitation.firstName;
		output.last_name = tylerCitation.lastName;
		output.date_of_birth = LocalDate.parse(tylerCitation.dob, dobFormatter);
		output.drivers_license_number = tylerCitation.driversLicenseNumber;

		List<LocalDateTime> violationCourtDates = null;
		violationCourtDates = tylerCitation.violations.stream()
				.map((violation) -> violation.courtDate)
				.distinct()
				.map(this::parseViolationCourtDate)
				.collect(Collectors.toList());
		output.court_dateTime = violationCourtDates.size() > 0 ? violationCourtDates.get(0) : null;

		output.violations = violationsFromTylerCitation(tylerCitation);

		// These could probably be added to the Tyler API
		// public LocalDate citation_date;
		// public String defendant_address;
		// public String defendant_city;
		// public String defendant_state;

		// These shouldn't be on our citation at all...
		// public String court_location;
		// public String court_address;

		// This is the hard one to fill out
		// public HashableEntity<Court> court_id;

		// There is no property for muni_id, but there probably should be

		return output;
	}

	public List<Violation> violationsFromTylerCitation(TylerCitation tylerCitation) {
		if (tylerCitation.violations != null) {
			return tylerCitation.violations.stream()
					.map(tylerViolation -> violationFromTylerViolation(tylerCitation, tylerViolation))
					.collect(Collectors.toList());
		}
		return null;
	}

	public Violation violationFromTylerViolation(TylerCitation tylerCitation, TylerViolation tylerViolation) {
		Violation output = new Violation();

		output.citation_number = tylerCitation.citationNumber;
		output.violation_number = tylerViolation.violationNumber;
		output.violation_description = tylerViolation.violationDescription;
		output.warrant_status = tylerViolation.warrantStatus;
		output.warrant_number = tylerViolation.warrantNumber;
		output.fine_amount = BigDecimal.valueOf(tylerViolation.fineAmount);

		TylerViolationStatus violationStatus = TylerViolationStatus.fromTylerViolationStatusString(tylerViolation.status);
		output.status = violationStatus != null ? violationStatus.getViolationStatus() : null;

		// These might be available in the Tyler API, but they aren't piped
		// through currently
		// public LocalDate status_date;
		// public BigDecimal court_cost;

		return output;
	}

}
