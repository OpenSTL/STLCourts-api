package svc.data.citations.datasources.tyler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
			return tylerCitations.stream().map(tylerCitation -> citationFromTylerCitation(tylerCitation))
					.collect(Collectors.toList());
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

		// public LocalDate citation_date - extract from violations
		// public LocalDateTime court_dateTime - Extract from violations

		// public String defendant_address; - Extract from violation maybe?
		// public String defendant_city;
		// public String defendant_state;

		// public String court_location; - These shouldn't be here at all...
		// public String court_address;

		output.violations = violationsFromTylerCitation(tylerCitation);

		// public HashableEntity<Court> court_id; - ???

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

		// public VIOLATION_STATUS status;
		// public LocalDate status_date;
		output.fine_amount = BigDecimal.valueOf(tylerViolation.fineAmount);
		// public BigDecimal court_cost;

		return output;
	}

}
