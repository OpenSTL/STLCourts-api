package svc.data.citations.datasources.tyler.transformers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.citations.datasources.tyler.models.TylerViolation;
import svc.data.citations.datasources.tyler.models.TylerViolationStatus;
import svc.logging.LogSystem;
import svc.models.Violation;

@Component
public class ViolationTransformer {

	public List<Violation> fromTylerCitation(TylerCitation tylerCitation) {
		if (tylerCitation.violations != null) {
			return tylerCitation.violations.stream()
					.map(tylerViolation -> fromTylerViolation(tylerCitation, tylerViolation))
					.collect(Collectors.toList());
		}
		return null;
	}

	public Violation fromTylerViolation(TylerCitation tylerCitation, TylerViolation tylerViolation) {

		Violation genericViolation = new Violation();
		genericViolation.citation_number = tylerCitation.citationNumber;
		genericViolation.violation_number = tylerViolation.violationNumber;
		genericViolation.violation_description = tylerViolation.violationDescription;
		genericViolation.warrant_status = tylerViolation.warrantStatus;
		genericViolation.warrant_number = tylerViolation.warrantNumber;
		genericViolation.fine_amount = BigDecimal.valueOf(tylerViolation.fineAmount);
		genericViolation.court_cost = null;

		TylerViolationStatus violationStatus = TylerViolationStatus.fromTylerViolationStatusString(tylerViolation.status);
		if (violationStatus == null) {
			LogSystem.LogEvent("Unrecognized Tyler Violation Status processed. A conversion should be added for status: " +
					tylerViolation.status);
		} else {
			genericViolation.status = violationStatus.getViolationStatus();
		}

		// These might be available in the Tyler API, but they aren't piped through currently
		// public BigDecimal court_cost;

		return genericViolation;
	}

}
