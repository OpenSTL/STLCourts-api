package svc.data.citations.datasources.importedITI.transformers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import svc.data.citations.datasources.importedITI.models.ImportedItiCitation;
import svc.data.citations.datasources.importedITI.models.ImportedItiViolation;
import svc.models.Violation;

@Component
public class ImportedItiViolationTransformer {

	public List<Violation> fromImportedCitation(ImportedItiCitation importedItiCitation) {
		if (importedItiCitation.violations != null) {
			return importedItiCitation.violations.stream()
					.map(importedItiViolation -> fromImportedItiViolation(importedItiCitation, importedItiViolation))
					.collect(Collectors.toList());
		}
		return null;
	}

	public Violation fromImportedItiViolation(ImportedItiCitation importedItiCitation, ImportedItiViolation importedItiViolation) {

		Violation genericViolation = new Violation();
		genericViolation.citation_number = importedItiCitation.citationNumber;
		genericViolation.violation_number = importedItiViolation.violationNumber;
		genericViolation.violation_description = importedItiViolation.violationDescription;
		genericViolation.warrant_status = importedItiViolation.warrantStatus;
		genericViolation.warrant_number = importedItiViolation.warrantNumber;
		genericViolation.fine_amount = importedItiViolation.fineAmount;
		genericViolation.can_pay_online = importedItiViolation.canPayOnline;
		genericViolation.court_cost = null;

		return genericViolation;
	}

}
