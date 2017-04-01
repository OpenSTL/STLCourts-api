package svc.data.citations.datasources.tyler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

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
import svc.data.citations.datasources.tyler.transformers.ViolationTransformer;
import svc.models.Citation;

@RunWith(MockitoJUnitRunner.class)
public class CitationTransformerTest {

	@Mock
	ViolationTransformer violationTransformer;

	@InjectMocks
	CitationTransformer citationTransformer;

	// public List<Citation> fromTylerCitations(List<TylerCitation> tylerCitations) {
	@Test
	public void citationTransformerReturnsNullForEmptyLists() {

		List<Citation> genericCitations = citationTransformer.fromTylerCitations(null);

		assertNull(genericCitations);
	}

	private List<TylerCitation> GenerateListOfTylerCitations() {
		return GenerateListOfTylerCitations(true);
	}

	private List<TylerCitation> GenerateListOfTylerCitations(boolean withCitations) {
		List<TylerCitation> listOfCitations = Lists.newArrayList(mock(TylerCitation.class));

		if (withCitations) {
			for (TylerCitation citation : listOfCitations) {
				citation.violations = GenerateListOfTylerViolations();
			}
		}

		return listOfCitations;
	}

	private List<TylerViolation> GenerateListOfTylerViolations() {
		return Lists.newArrayList(mock(TylerViolation.class));
	}

	@Test
	public void citationTransformerTransformsAllCitationsPassedIn() {

		List<TylerCitation> tylerCitations = GenerateListOfTylerCitations();

		List<Citation> genericCitations = citationTransformer.fromTylerCitations(tylerCitations);

		assertNotNull(genericCitations);
		assertEquals(tylerCitations.size(), genericCitations.size());
	}

	// public Citation fromTylerCitation(TylerCitation tylerCitation) {
	@Test
	public void citationTransformerReturnsNullForNullCitation() {

		Citation genericCitation = citationTransformer.fromTylerCitation(null);

		assertNull(genericCitation);
	}
}
