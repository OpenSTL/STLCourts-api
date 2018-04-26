package svc.data.citations.datasources.importedITI.transformers;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import svc.data.citations.datasources.importedITI.models.ImportedItiCitation;
import svc.data.citations.datasources.importedITI.models.ImportedItiViolation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.data.transformer.CitationDateTimeTransformer;
import svc.models.Citation;
import svc.models.Court;
import svc.models.Municipality;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class ImportedItiCitationTransformerTest {

	@Mock
	ImportedItiViolationTransformer violationTransformer;

	@Mock
	CourtIdTransformer courtIdTransformer;

	@InjectMocks
	ImportedItiCitationTransformer citationTransformer;

	@Mock
	MunicipalityIdTransformer municipalityIdTransformer;
	
	@Mock
	CitationDateTimeTransformer citationDateTimeTransformer;
	
	@Test
	public void citationTransformerReturnsNullForEmptyLists() {

		List<Citation> genericCitations = citationTransformer.fromImportedItiCitations(null);

		assertNull(genericCitations);
	}

	private List<ImportedItiCitation> generateListOfImportedItiCitations() {
		return generateListOfImportedItiCitations(true);
	}

	private List<ImportedItiCitation> generateListOfImportedItiCitations(boolean withCitations) {
		List<ImportedItiCitation> listOfCitations = Lists.newArrayList(mock(ImportedItiCitation.class));

		if (withCitations) {
			for (ImportedItiCitation citation : listOfCitations) {
				citation.violations = generateListOfImportedViolations();
			}
		}

		return listOfCitations;
	}

	private List<ImportedItiViolation> generateListOfImportedViolations() {
		return Lists.newArrayList(mock(ImportedItiViolation.class));
	}

	@Test
	public void citationTransformerTransformsAllCitationsPassedIn() {
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityIdFromCourtId(anyLong()))
		.thenReturn(municipalHashable);
		
		List<ImportedItiCitation> importedItiCitations = generateListOfImportedItiCitations();

		List<Citation> genericCitations = citationTransformer.fromImportedItiCitations(importedItiCitations);

		assertNotNull(genericCitations);
		assertEquals(importedItiCitations.size(), genericCitations.size());
	}

	@Test
	public void citationTransformerReturnsNullForNullCitation() {

		Citation genericCitation = citationTransformer.fromImportedItiCitation(null);

		assertNull(genericCitation);
	}

	private ImportedItiCitation generateFullImportedItiCitation() {
		ImportedItiCitation mockCitation = mock(ImportedItiCitation.class);
		mockCitation.dateOfBirth = "1900-06-17";
		mockCitation.citationDate = "1901-06-17";
		mockCitation.courtDateTime = "1902-06-17T19:00:00.000";
		mockCitation.violations = generateListOfImportedViolations();
		mockCitation.violations.get(0).citationNumber = "ABC";
		mockCitation.courtId = 4L;

		return mockCitation;
	}

	
	@Test
	public void citationTransformerCopiesCitationFieldsCorrectly() {
		ImportedItiCitation importedItiCitation = generateFullImportedItiCitation();
		// final HashableEntity<Court> courtHashable = new HashableEntity<Court>(Court.class, importedItiCitation.courtId);
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		final ZonedDateTime zonedCourtDateTime = ZonedDateTime.of(LocalDateTime.parse("1902-06-17T19:00:00.000"),ZoneId.of("America/Chicago"));
		when(municipalityIdTransformer.lookupMunicipalityIdFromCourtId(anyLong()))
		.thenReturn(municipalHashable);
		when(citationDateTimeTransformer.transformLocalDateTime(any(), any())).thenReturn(zonedCourtDateTime);
		
		Citation genericCitation = citationTransformer.fromImportedItiCitation(importedItiCitation);

		assertNotNull(genericCitation);
		assertEquals(genericCitation.citation_number, importedItiCitation.citationNumber);
		assertEquals(genericCitation.first_name, importedItiCitation.firstName);
		assertEquals(genericCitation.last_name, importedItiCitation.lastName);
		assertEquals(genericCitation.drivers_license_number, importedItiCitation.driversLicenseNumber);
		assertEquals(genericCitation.date_of_birth, LocalDate.parse("1900-06-17"));
		assertEquals(genericCitation.citation_date, LocalDate.parse("1901-06-17"));
		assertEquals(genericCitation.court_dateTime, zonedCourtDateTime);
		assertEquals(genericCitation.municipality_id, municipalHashable);

		verify(violationTransformer).fromImportedCitation(importedItiCitation);
		verify(municipalityIdTransformer).lookupMunicipalityIdFromCourtId(anyLong());
	}
	
}
