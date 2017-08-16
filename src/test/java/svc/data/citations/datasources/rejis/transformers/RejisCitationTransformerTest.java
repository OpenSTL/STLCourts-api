package svc.data.citations.datasources.rejis.transformers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import svc.data.citations.datasources.CITATION_DATASOURCE;
import svc.data.citations.datasources.rejis.models.RejisFullCitation;
import svc.data.citations.datasources.rejis.models.RejisPartialCitation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Municipality;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class RejisCitationTransformerTest {

	@Mock
	RejisViolationTransformer violationTransformer;

	@Mock
	CourtIdTransformer courtIdTransformer;

	@InjectMocks
	RejisCitationTransformer citationTransformer;

	@Mock
	MunicipalityIdTransformer municipalityIdTransformer;
	
	@Test
	public void citationTransformerReturnsNullForNullFullCitation() {

		Citation citation = citationTransformer.fromRejisFullCitation(null, new RejisPartialCitation());

		assertNull(citation);
	}

	@Test
	public void citationTransformerReturnsNullForNullPartialCitation() {

		Citation citation = citationTransformer.fromRejisFullCitation(new RejisFullCitation(), null);

		assertNull(citation);
	}

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
		
		return mockCitation;
	}
	
	private RejisPartialCitation generatePartialRejisCitation() {
		RejisPartialCitation mockCitation = new RejisPartialCitation();
		mockCitation.ShowIpaycourt = false;
		return mockCitation;
	}

	@Test
	public void citationTransformerTransformsRejisCitation() {
		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		
		final HashableEntity<Court> courtHashable = new HashableEntity<Court>(Court.class,5L);
		when(courtIdTransformer.lookupCourtId(CITATION_DATASOURCE.REJIS, rejisFullCitation.AgcyId))
		.thenReturn(courtHashable);
		
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityId(CITATION_DATASOURCE.REJIS, rejisFullCitation.AgcyId))
		.thenReturn(municipalHashable);
		
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());

		assertNotNull(citation);
		assertEquals(citation.citation_number, rejisFullCitation.TktNum);
		assertEquals(citation.first_name, "Adam");
		assertEquals(citation.last_name, "Lastapple");
		assertEquals(citation.drivers_license_number, "");
		assertEquals(citation.drivers_license_state, "");
		assertEquals(citation.date_of_birth, LocalDateTime.parse(rejisFullCitation.Dob).toLocalDate());
		assertEquals(citation.citation_date, LocalDateTime.parse(rejisFullCitation.ViolDttm).toLocalDate());
		assertEquals(citation.court_dateTime, LocalDateTime.parse(rejisFullCitation.NextDktDate));
		assertEquals(citation.court_id, courtHashable);
		assertEquals(citation.municipality_id, municipalHashable);
		assertEquals(citation.defendant_address, "123 AnyStreet");
		assertEquals(citation.defendant_city, "Anytown");
		assertEquals(citation.defendant_state, "MO");
	}

	@Test
	public void citationTransformerReturnsNullForNoDob() {

		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		rejisFullCitation.Dob = "";
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());
		
		assertNull(citation);
	}
	
	@Test
	public void citationTransformerReturnsNullForNoViolationDate() {

		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		rejisFullCitation.ViolDttm = "";
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());
		
		assertNull(citation);
	}
	
	@Test
	public void citationTransformerReturnsNullForNoNextCourtDate() {

		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		rejisFullCitation.NextDktDate = "";
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());
		
		assertNull(citation);
	}
	
	@Test
	public void citationTransformerReturnsNullForNoOriginalCourtDate() {

		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		rejisFullCitation.OrigDktDate = "";
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());
		
		assertNull(citation);
	}
	
	@Test
	public void citationTransformerCorrectlyAssignsCourtDate() {
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityId(anyObject(),anyString()))
		.thenReturn(municipalHashable);
		
		RejisFullCitation rejisFullCitation = generateFullRejisCitation();
		rejisFullCitation.OrigDktDate = "2017-10-01T12:30:00";
		Citation citation = citationTransformer.fromRejisFullCitation(rejisFullCitation, generatePartialRejisCitation());

		assertEquals(citation.court_dateTime, LocalDateTime.parse(rejisFullCitation.OrigDktDate));
	}

}
