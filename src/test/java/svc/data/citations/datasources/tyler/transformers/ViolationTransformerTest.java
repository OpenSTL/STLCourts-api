package svc.data.citations.datasources.tyler.transformers;

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

import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.citations.datasources.tyler.models.TylerViolation;
import svc.models.VIOLATION_STATUS;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class ViolationTransformerTest {
	@InjectMocks
	ViolationTransformer mockViolationTransformer;
	
	TylerCitation tylerCitation;
	TylerViolation tylerViolation;
	
	@Before
	public void setUp(){
		tylerCitation = new TylerCitation();
		tylerViolation = new TylerViolation();
		tylerCitation.citationNumber = "F123";
		tylerViolation.violationNumber = "V123";
		tylerViolation.violationDescription = "ABC";
		tylerViolation.warrantStatus = true;
		tylerViolation.warrantNumber = "W123";
		tylerViolation.fineAmount = 23.00;
		tylerViolation.status = "WARRANT ISSUED";
	}
	
	@Test
	public void returnsGenericViolationFromTylerViolation(){
		Violation violation = mockViolationTransformer.fromTylerViolation(tylerCitation, tylerViolation);
		
		assertThat(violation.citation_number,is("F123"));
		assertThat(violation.violation_number,is("V123"));
		assertThat(violation.violation_description,is("ABC"));
		assertThat(violation.warrant_status,is(true));
		assertThat(violation.warrant_number,is("W123"));
		assertThat(violation.fine_amount,is(BigDecimal.valueOf(23.00)));
		assertThat(violation.court_cost,is(nullValue()));
		assertThat(violation.status,is(VIOLATION_STATUS.FTA_WARRANT_ISSUED));
	}
	
	@Test
	public void returnsGenericViolationListFromTylerCitation(){
		tylerCitation.violations = Lists.newArrayList(tylerViolation);
		List<Violation> violations = mockViolationTransformer.fromTylerCitation(tylerCitation);
		
		assertNotNull(violations);
		assertThat(violations.size(),is(1));
		assertThat(violations.get(0).citation_number,is("F123"));
		assertThat(violations.get(0).violation_number,is("V123"));
		assertThat(violations.get(0).violation_description,is("ABC"));
		assertThat(violations.get(0).warrant_status,is(true));
		assertThat(violations.get(0).warrant_number,is("W123"));
		assertThat(violations.get(0).fine_amount,is(BigDecimal.valueOf(23.00)));
		assertThat(violations.get(0).court_cost,is(nullValue()));
		assertThat(violations.get(0).status,is(VIOLATION_STATUS.FTA_WARRANT_ISSUED));
	}

}
