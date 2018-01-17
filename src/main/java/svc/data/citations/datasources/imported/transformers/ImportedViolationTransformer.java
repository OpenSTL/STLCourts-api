package svc.data.citations.datasources.imported.transformers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.imported.models.ImportedCitation;
import svc.data.citations.datasources.imported.models.ImportedViolation;
import svc.models.Violation;

@Component
public class ImportedViolationTransformer {

	public List<Violation> fromImportedCitation(ImportedCitation importedCitation) {
		if (importedCitation.violations != null) {
			return importedCitation.violations.stream()
					.map(importedViolation -> fromImportedViolation(importedCitation, importedViolation))
					.collect(Collectors.toList());
		}
		return null;
	}

	public Violation fromImportedViolation(ImportedCitation importedCitation, ImportedViolation importedViolation) {

		Violation genericViolation = new Violation();
		genericViolation.citation_number = importedCitation.citationNumber;
		genericViolation.violation_number = importedViolation.violationNumber;
		genericViolation.violation_description = importedViolation.violationDescription;
		genericViolation.warrant_status = importedViolation.warrantStatus;
		genericViolation.warrant_number = importedViolation.warrantNumber;
		genericViolation.fine_amount = importedViolation.fineAmount;
		genericViolation.can_pay_online = importedViolation.canPayOnline;
		genericViolation.court_cost = null;

		return genericViolation;
	}

}
