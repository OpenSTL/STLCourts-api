package svc.data.citations.datasources.imported.transformers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
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

import svc.data.citations.datasources.imported.models.ImportedCitation;
import svc.data.citations.datasources.imported.models.ImportedViolation;
import svc.data.citations.datasources.transformers.CourtIdTransformer;
import svc.data.citations.datasources.transformers.MunicipalityIdTransformer;
import svc.models.Citation;
import svc.models.Municipality;
import svc.types.HashableEntity;

@RunWith(MockitoJUnitRunner.class)
public class ImportedCitationTransformerTest {

	@Mock
	ImportedViolationTransformer violationTransformer;

	@Mock
	CourtIdTransformer courtIdTransformer;

	@InjectMocks
	ImportedCitationTransformer citationTransformer;

	@Mock
	MunicipalityIdTransformer municipalityIdTransformer;
	
	@Test
	public void citationTransformerReturnsNullForEmptyLists() {

		List<Citation> genericCitations = citationTransformer.fromImportedCitations(null);

		assertNull(genericCitations);
	}

	private List<ImportedCitation> generateListOfImportedCitations() {
		return generateListOfImportedCitations(true);
	}

	private List<ImportedCitation> generateListOfImportedCitations(boolean withCitations) {
		List<ImportedCitation> listOfCitations = Lists.newArrayList(mock(ImportedCitation.class));

		if (withCitations) {
			for (ImportedCitation citation : listOfCitations) {
				citation.violations = generateListOfImportedViolations();
			}
		}

		return listOfCitations;
	}

	private List<ImportedViolation> generateListOfImportedViolations() {
		return Lists.newArrayList(mock(ImportedViolation.class));
	}

	@Test
	public void citationTransformerTransformsAllCitationsPassedIn() {
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityIdFromCourtId(anyLong()))
		.thenReturn(municipalHashable);
		
		List<ImportedCitation> importedCitations = generateListOfImportedCitations();

		List<Citation> genericCitations = citationTransformer.fromImportedCitations(importedCitations);

		assertNotNull(genericCitations);
		assertEquals(importedCitations.size(), genericCitations.size());
	}

	@Test
	public void citationTransformerReturnsNullForNullCitation() {

		Citation genericCitation = citationTransformer.fromImportedCitation(null);

		assertNull(genericCitation);
	}

	private ImportedCitation generateFullImportedCitation() {
		ImportedCitation mockCitation = mock(ImportedCitation.class);
		mockCitation.dateOfBirth = "1900-06-17";
		mockCitation.citationDate = "1901-06-17";
		mockCitation.courtDateTime = "1902-06-17T19:00:00.000";
		mockCitation.violations = generateListOfImportedViolations();
		mockCitation.violations.get(0).citationNumber = "ABC";

		return mockCitation;
	}

	
	@Test
	public void citationTransformerCopiesCitationFieldsCorrectly() {
		ImportedCitation importedCitation = generateFullImportedCitation();
		final HashableEntity<Municipality> municipalHashable = new HashableEntity<Municipality>(Municipality.class,3L);
		when(municipalityIdTransformer.lookupMunicipalityIdFromCourtId(anyLong()))
		.thenReturn(municipalHashable);
		
		Citation genericCitation = citationTransformer.fromImportedCitation(importedCitation);

		assertNotNull(genericCitation);
		assertEquals(genericCitation.citation_number, importedCitation.citationNumber);
		assertEquals(genericCitation.first_name, importedCitation.firstName);
		assertEquals(genericCitation.last_name, importedCitation.lastName);
		assertEquals(genericCitation.drivers_license_number, importedCitation.driversLicenseNumber);
		assertEquals(genericCitation.date_of_birth, LocalDate.parse("1900-06-17"));
		assertEquals(genericCitation.citation_date, LocalDate.parse("1901-06-17"));
		assertEquals(genericCitation.court_dateTime, LocalDateTime.parse("1902-06-17T19:00:00.000"));
		assertEquals(genericCitation.municipality_id, municipalHashable);

		verify(violationTransformer).fromImportedCitation(importedCitation);
		verify(municipalityIdTransformer).lookupMunicipalityIdFromCourtId(anyLong());
	}
	
}
