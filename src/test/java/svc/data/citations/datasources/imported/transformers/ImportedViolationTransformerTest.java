package svc.data.citations.datasources.imported.transformers;

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

import svc.data.citations.datasources.imported.models.ImportedCitation;
import svc.data.citations.datasources.imported.models.ImportedViolation;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class ImportedViolationTransformerTest {
	@InjectMocks
	ImportedViolationTransformer mockViolationTransformer;
	
	ImportedCitation importedCitation;
	ImportedViolation importedViolation;
	
	@Before
	public void setUp(){
		importedCitation = new ImportedCitation();
		importedViolation = new ImportedViolation();
		importedCitation.citationNumber = "F123";
		importedViolation.violationNumber = "V123";
		importedViolation.violationDescription = "ABC";
		importedViolation.warrantStatus = true;
		importedViolation.warrantNumber = "W123";
		importedViolation.fineAmount = new BigDecimal("23.00");
		importedViolation.canPayOnline = true;
	}
	
	@Test
	public void returnsGenericViolationFromTylerViolation(){
		Violation violation = mockViolationTransformer.fromImportedViolation(importedCitation, importedViolation);
		
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
	public void returnsGenericViolationListFromTylerCitation(){
		importedCitation.violations = Lists.newArrayList(importedViolation);
		List<Violation> violations = mockViolationTransformer.fromImportedCitation(importedCitation);
		
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
