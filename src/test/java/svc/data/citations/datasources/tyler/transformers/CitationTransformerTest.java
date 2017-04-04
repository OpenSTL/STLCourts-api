package svc.data.citations.datasources.tyler.transformers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.tyler.models.TylerCitation;
import svc.data.citations.datasources.tyler.models.TylerViolation;
import svc.data.citations.datasources.tyler.transformers.CitationTransformer;
import svc.data.citations.datasources.tyler.transformers.CourtIdTransformer;
import svc.data.citations.datasources.tyler.transformers.ViolationTransformer;
import svc.models.Citation;
import svc.models.Municipality;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class CitationTransformerTest {

	@Mock
	ViolationTransformer violationTransformer;

	@Mock
	CourtIdTransformer courtIdTransformer;

	@InjectMocks
	CitationTransformer citationTransformer;

	@Mock
	MunicipalityIdTransformer municipalityIdTransformer;
	
	@Test
	public void citationTransformerReturnsNullForEmptyLists() {

		List<Citation> genericCitations = citationTransformer.fromTylerCitations(null);

		assertNull(genericCitations);
	}

	private List<TylerCitation> generateListOfTylerCitations() {
		return generateListOfTylerCitations(true);
	}

	private List<TylerCitation> generateListOfTylerCitations(boolean withCitations) {
		List<TylerCitation> listOfCitations = Lists.newArrayList(mock(TylerCitation.class));

		if (withCitations) {
			for (TylerCitation citation : listOfCitations) {
				citation.violations = generateListOfTylerViolations();
			}
		}

		return listOfCitations;
	}

	private List<TylerViolation> generateListOfTylerViolations() {
		return Lists.newArrayList(mock(TylerViolation.class));
	}

	@Test
	public void citationTransformerTransformsAllCitationsPassedIn() {
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityId(anyString(),anyString()))
		.thenReturn(municipalHashable);
		
		List<TylerCitation> tylerCitations = generateListOfTylerCitations();

		List<Citation> genericCitations = citationTransformer.fromTylerCitations(tylerCitations);

		assertNotNull(genericCitations);
		assertEquals(tylerCitations.size(), genericCitations.size());
	}

	@Test
	public void citationTransformerReturnsNullForNullCitation() {

		Citation genericCitation = citationTransformer.fromTylerCitation(null);

		assertNull(genericCitation);
	}

	private TylerCitation generateFullTylerCitation() {
		TylerCitation mockCitation = mock(TylerCitation.class);
		mockCitation.dob = "06/17/1900";
		mockCitation.violationDate = "06/17/1901";

		mockCitation.violations = generateListOfTylerViolations();
		mockCitation.violations.get(0).courtDate = "1902-06-17T19:00:00.000";
		mockCitation.violations.get(0).courtName = "A";

		return mockCitation;
	}

	@Test
	public void citationTransformerCopiesCitationFieldsCorrectly() {
		TylerCitation tylerCitation = generateFullTylerCitation();
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityId(anyString(),anyString()))
		.thenReturn(municipalHashable);
		
		Citation genericCitation = citationTransformer.fromTylerCitation(tylerCitation);

		assertNotNull(genericCitation);
		assertEquals(genericCitation.citation_number, tylerCitation.citationNumber);
		assertEquals(genericCitation.first_name, tylerCitation.firstName);
		assertEquals(genericCitation.last_name, tylerCitation.lastName);
		assertEquals(genericCitation.drivers_license_number, tylerCitation.driversLicenseNumber);
		assertEquals(genericCitation.date_of_birth, LocalDate.parse("1900-06-17"));
		assertEquals(genericCitation.citation_date, LocalDate.parse("1901-06-17"));
		assertEquals(genericCitation.court_dateTime, LocalDateTime.parse("1902-06-17T19:00:00.000"));
		assertEquals(genericCitation.municipality_id, municipalHashable);

		verify(violationTransformer).fromTylerCitation(tylerCitation);
		verify(courtIdTransformer).lookupCourtId("A");
	}
}
