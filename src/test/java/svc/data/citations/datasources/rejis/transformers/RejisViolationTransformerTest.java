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
		mockCitation.ticketNumber = "123";
		mockCitation.dob = "1900-06-01T00:00:00";
		mockCitation.violationDateTime = "1901-06-17T13:30:00";
		mockCitation.balanceDue = 4.78;
		mockCitation.caseStatus = "W";
		mockCitation.defendantName = "Adam Apple";
		mockCitation.lastName = "LastApple";
		mockCitation.defendantAddress = "123 AnyStreet  Anytown, MO  12345";
		mockCitation.nextCourtDate = "2017-09-01T12:30:00";
		mockCitation.originalCourtDate = "2017-08-01T12:30:00";
		mockCitation.agencyId = "B";
		mockCitation.chargeDescription = "Some Description";
		
		return mockCitation;
	}
	
	private RejisPartialCitation generatePartialRejisCitation() {
		RejisPartialCitation mockCitation = new RejisPartialCitation();
		mockCitation.showIpaycourt = false;
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
		assertEquals(violation.citation_number, rejisFullCitation.ticketNumber);
		assertEquals(violation.violation_number, rejisFullCitation.ticketNumber);
		assertEquals(violation.violation_description, rejisFullCitation.chargeDescription);
		assertEquals(violation.warrant_status, true);
		assertEquals(violation.warrant_number, "");
		assertEquals(violation.fine_amount, BigDecimal.valueOf(rejisFullCitation.balanceDue));
		assertEquals(violation.can_pay_online, rejisPartialCitation.showIpaycourt);
		assertNull(violation.court_cost);
	}

}
