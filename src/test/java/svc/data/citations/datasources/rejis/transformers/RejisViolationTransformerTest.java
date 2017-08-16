package svc.data.citations.datasources.rejis.transformers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.models.Violation;

@RunWith(MockitoJUnitRunner.class)
public class RejisViolationTransformerTest {
	@InjectMocks
	RejisViolationTransformer violationTransformer;
	

	private RejisFullCitation generateFullRejisCitation() {
		RejisFullCitation mockCitation = new RejisFullCitation();
		mockCitation.TktNum = "123";
		mockCitation.Dob = "1900-06-01T00:00:00";
		mockCitation.ViolDttm = "1901-06-17T13:30:00";
		mockCitation.BalDue = 4.78;
		mockCitation.CaseStatus = "W";
		mockCitation.DeftName = "Adam Apple";
		mockCitation.LastName = "LastApple";
		mockCitation.DeftAddr = "123 AnyStreet  Anytown, MO  12345";
		mockCitation.NextDktDate = "2017-09-01T12:30:00";
		mockCitation.OrigDktDate = "2017-08-01T12:30:00";
		mockCitation.AgcyId = "B";
		mockCitation.ChrgDesc = "Some Description";
		
		return mockCitation;
	}
	
	private RejisPartialCitation generatePartialRejisCitation() {
		RejisPartialCitation mockCitation = new RejisPartialCitation();
		mockCitation.ShowIpaycourt = false;
		return mockCitation;
	}

	@Test
	public void violationTransformerTransformsRejisCitation() {
		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		RejisPartialCitation rejisPartialCitation = generatePartialRejisCitation();
		
		List<Violation> violations= violationTransformer.fromRejisFullCitation(rejisFullCitation, rejisPartialCitation);

		assertNotNull(violations);
		assertEquals(violations.size(), 1);
		
		Violation violation = violations.get(0);
		assertEquals(violation.citation_number, rejisFullCitation.TktNum);
		assertEquals(violation.violation_number, rejisFullCitation.TktNum);
		assertEquals(violation.violation_description, rejisFullCitation.ChrgDesc);
		assertEquals(violation.warrant_status, true);
		assertEquals(violation.warrant_number, "");
		assertEquals(violation.fine_amount, BigDecimal.valueOf(rejisFullCitation.BalDue));
		assertEquals(violation.can_pay_online, rejisPartialCitation.ShowIpaycourt);
		assertNull(violation.court_cost);
	}

}
