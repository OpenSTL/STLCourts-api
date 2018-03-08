package svc.data.citations.datasources.importedITI.transformers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.importedITI.models.ImportedItiCitation;
import svc.data.citations.datasources.importedITI.models.ImportedItiViolation;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class ImportedItiViolationTransformerTest {
	@InjectMocks
	ImportedItiViolationTransformer mockViolationTransformer;
	
	ImportedItiCitation importedItiCitation;
	ImportedItiViolation importedItiViolation;
	
	@Before
	public void setUp(){
		importedItiCitation = new ImportedItiCitation();
		importedItiViolation = new ImportedItiViolation();
		importedItiCitation.citationNumber = "F123";
		importedItiViolation.violationNumber = "V123";
		importedItiViolation.violationDescription = "ABC";
		importedItiViolation.warrantStatus = true;
		importedItiViolation.warrantNumber = "W123";
		importedItiViolation.fineAmount = new BigDecimal("23.00");
		importedItiViolation.canPayOnline = true;
	}
	
	@Test
	public void returnsGenericViolationFromImportedItiViolation(){
		Violation violation = mockViolationTransformer.fromImportedItiViolation(importedItiCitation, importedItiViolation);
		
		assertThat(violation.citation_number,is("F123"));
		assertThat(violation.violation_number,is("V123"));
		assertThat(violation.violation_description,is("ABC"));
		assertThat(violation.warrant_status,is(true));
		assertThat(violation.warrant_number,is("W123"));
		assertThat(violation.fine_amount,is(new BigDecimal("23.00")));
		assertThat(violation.court_cost,is(nullValue()));
		assertThat(violation.can_pay_online, is(true));
	}
	
	@Test
	public void returnsGenericViolationListFromImportedItiCitation(){
		importedItiCitation.violations = Lists.newArrayList(importedItiViolation);
		List<Violation> violations = mockViolationTransformer.fromImportedCitation(importedItiCitation);
		
		assertNotNull(violations);
		assertThat(violations.size(),is(1));
		assertThat(violations.get(0).citation_number,is("F123"));
		assertThat(violations.get(0).violation_number,is("V123"));
		assertThat(violations.get(0).violation_description,is("ABC"));
		assertThat(violations.get(0).warrant_status,is(true));
		assertThat(violations.get(0).warrant_number,is("W123"));
		assertThat(violations.get(0).fine_amount,is(new BigDecimal("23.00")));
		assertThat(violations.get(0).court_cost,is(nullValue()));
	}

}
